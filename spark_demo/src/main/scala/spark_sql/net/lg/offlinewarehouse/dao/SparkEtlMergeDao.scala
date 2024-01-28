package spark_sql.net.lg.offlinewarehouse.dao

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions._
import spark_sql.net.lg.offlinewarehouse.utils.ProcessUtils._
import spark_sql.net.lg.offlinewarehouse.utils.SparkUtils

/**
  * @Author ：Allen
  * @Project ：bigdata_demo
  * @Name ：SparkEtlMergeDao
  * @Date ：2024年01月27日 0027 12:14:19
  * @Info ：处理sparkMerge程序
  */
object SparkEtlMergeDao {
  private val spark: SparkSession = SparkUtils.getSparkSession()

  def executeMergeMode(mode:String, dataSource:String, tgtTableName:String, keyCol:String): Unit = {
    printMessage(s"Begin to process merge mode, the mode is :${mode}")
    val tableFields: Array[String] = tgtTableName.split("\\.")
    val tableOwner = tableFields(0)
    val tableName = tableFields(1)
    var newData: DataFrame = null

    if ("fileMode".equals(mode)) {
      // 读取源路径数据
      printMessage(s"the target tableName is : ${tableName}")
      printMessage(s"the key is: ${keyCol}")
      printMessage(s"the file path is : ${dataSource}")
      newData = spark.read.parquet(dataSource)
    } else if ("sqlMode".equals(mode)) {
      // 读取sql查询数据
      printMessage(s"the target tableName is : ${tableName}")
      printMessage(s"the key is: ${keyCol}")
      printMessage(s"the sql is :\n ${dataSource}")
      newData = spark.sql(dataSource)
    }

    // 获取历史数据
    val oldData: DataFrame = spark.table(tgtTableName)
    // 获取历史未更新的数据
    val oldJoinData: DataFrame = oldData.join(newData, keyCol.split("\\s+"), "left_anti")
    // 1.关联数据优先取新数据集
    // 2.并上老数据集中非新增数据
    // 3.新增字段插入时间取第一次插入时间，更新时间取当前时间
    val resultData: DataFrame = newData.join(oldJoinData, keyCol.split("\\s+"), "left")
      .select(newData("*"), coalesce(oldData("inserttime"), lit(date_format(current_timestamp(), "yyyy-MM-dd HH:mm:ss"))).as("inserttime"))
      .withColumn("updatetime", lit(date_format(current_timestamp(), "yyyy-MM-dd HH:mm:ss"))).union(oldData)

    try {
      // 创建数据临时表
      resultData.write.mode(SaveMode.Overwrite).saveAsTable(s"${tgtTableName}_tmp_001")
      // 覆盖最终结果数据
      spark.sql(s"insert overwrite table ${tgtTableName} select * from ${tgtTableName}_tmp_001")
    } catch {
      case nst:Exception =>
        printMessage(nst.getMessage)
    } finally {
      // 删除最终数据
      spark.sql(s"drop table if exists ${tgtTableName}_tmp_001")
    }
  }
}