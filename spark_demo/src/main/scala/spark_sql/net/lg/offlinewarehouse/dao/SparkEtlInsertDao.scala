package spark_sql.net.lg.offlinewarehouse.dao

import java.io.File

import org.apache.spark.sql.catalyst.analysis.NoSuchTableException
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import spark_sql.net.lg.offlinewarehouse.model.TableFileStat
import spark_sql.net.lg.offlinewarehouse.utils.ProcessUtils._
import spark_sql.net.lg.offlinewarehouse.utils.SparkUtils._

/**
  * @Author ：Allen
  * @Project ：bigdata_demo
  * @Name ：SparkEtlDao
  * @Date ：2024年01月26日 0026 22:02:25
  * @Info ：处理与spark直接插入表数据
  */
object SparkEtlInsertDao {
  // 创建sparkSession对象
  private val spark: SparkSession = getSparkSession()

  def executeInsertMode(sql:String, tgtTableName:String, mode:String): Unit = {
    printMessage("Begin to process the data. the sql is :\n" + sql)
    // 获取表名
    val tableName:String = tgtTableName.split("\\.")(1)
    println(s"tableName: $tableName")
    // 获取到待执行sql的DataFrame
    val newData:DataFrame = spark.sql(sql)
    printMessage("保存输出数据")
    printMessage("保存数据格式: " + (if ("0".equals(mode)) "覆盖" else "追加"))

    //判断执行模式是否为覆盖模式
    if ("0".equals(mode)) {
      try {
        //创建新执行结果的视图
        newData.createOrReplaceTempView(s"${tableName}_tmp_new_view")
        //覆盖目标表
        spark.sql(s"INSERT OVERWRITE TABLE ${tgtTableName} SELECT * FROM ${tableName}_tmp_new_view")
      } catch {
        //如果最终表不存在则重新创建
        case nst:NoSuchTableException =>
          newData.write.mode(SaveMode.Overwrite).saveAsTable(tgtTableName)
      } finally {
        spark.sql(s"INSERT OVERWRITE TABLE ${tgtTableName} SELECT * FROM ${tableName}_tmp_new_view")
      }
    } else if ("1".equals(mode)) { //判断执行模式是否为追加模式
      try {
        //获取表文件夹的状态信息
        val tableFileStat:TableFileStat = getTableFileNum(tgtTableName)
        //判断文件数量是否大于800
        if (tableFileStat.originFileNum > 800) {
          printMessage(s"file number great then 800, now is ${tableFileStat.originFileNum}")
          //获取表的hdfs全路径
          val table_path = spark.sqlContext.getAllConfs.get("spark.sql.warehouse.dir").mkString + File.separator + generateDir(tgtTableName.toLowerCase())
          //将新老数据合并并按照150M每分区大小重分区
          spark.read.parquet(table_path).union(newData)
            .repartition((tableFileStat.totalSize/1258291201 + 1).intValue())
            .write.mode(SaveMode.Overwrite).saveAsTable(s"${tgtTableName}_tmp_001")
          //将新数据注册为中间结果表
          spark.sql(s"INSERT OVERWRITE TABLE ${tgtTableName} SELECT * FROM ${tgtTableName}_tmp_001")
        } else {
          //文件数量小于800时直接通过中间表覆盖原表
          printMessage(s"file number less then 800 num is ${tableFileStat.originFileNum}")
          newData.createOrReplaceTempView(s"${tableName}_tmp_new_view")
          spark.sql(s"INSERT OVERWRITE TABLE ${tgtTableName} SELECT * FROM ${tableName}_tmp_new_view")
        }
      } catch {
        case nst:NoSuchTableException =>
          printMessage("输出表不存在, 新建目标表，并将数据写入目标表中")
          newData.write.mode(SaveMode.Overwrite).saveAsTable(tgtTableName)
      } finally {
        //删除中间表
        spark.sql("DROP TABLE IF EXISTS ${outPutTableName}_tmp_001")
      }
    }
  }

}
