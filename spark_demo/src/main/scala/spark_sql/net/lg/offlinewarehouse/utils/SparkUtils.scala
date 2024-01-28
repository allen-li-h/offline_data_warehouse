package spark_sql.net.lg.offlinewarehouse.utils

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.TableIdentifier
import spark_sql.net.lg.offlinewarehouse.model.TableFileStat
import spark_sql.net.lg.offlinewarehouse.utils.ProcessUtils._


/**
  * @Author ：Allen
  * @Project ：bigdata_demo
  * @Name ：SparkUtils
  * @Date ：2024年01月27日 0027 11:25:09
  * @Info ：spark包公用类
  */
object SparkUtils {
  // 设置日志输出级别
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
  private var spark:SparkSession = null

  /**
    * 设置sparkSession信息
    * @return SparkSession信息
    */
  def getSparkSession(): SparkSession = {
//    System.setProperty("HADOOP_USER_NAME", "hdfs")
    spark = SparkSession.builder().enableHiveSupport() //.master("local[*]").appName("111")
      .getOrCreate()
    spark
  }



  /**
    * 获取表文件夹的状态信息
    * @param spark
    * @param tgtTableName
    * @return
    */
  def getTableFileNum(tgtTableName:String):TableFileStat = {
    val table_message = tgtTableName.split("\\.") //获取到表名和库名
    //获取表的路径名称
    val table_path:String = spark.sessionState.catalog.getTableMetadata(TableIdentifier(table_message(1), Option(table_message(0))))
      .location.getPath

    //获取hdfs文件系统对象
    val fs:FileSystem = FileSystem.get(new URI(table_path), new Configuration())
    //获取到表文件夹的文件对象
    val files = fs.listStatus(new Path(table_path))

    printMessage(s"table hdfs directory: ${table_path}")
    var originFileNum = -1
    var length:Long = 1

    //循环遍历文件夹的子文件
    for (f <- files) {
      length = f.getLen + length  //累加文件大小
      originFileNum = originFileNum + 1  //累加文件数量
    }
    printMessage(s"totalSize if table: ${length}")
    TableFileStat(originFileNum, length)
  }
}
