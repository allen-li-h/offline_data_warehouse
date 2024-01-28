package spark_sql.net.lg.offlinewarehouse.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
  * @Author ：Allen
  * @Project ：bigdata_demo
  * @Name ：ProcessUtils
  * @Date ：2024年01月27日 0027 12:08:47
  * @Info ：程序工具类
  */
object ProcessUtils {
  // 创建日期格式化对象
  private val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


  /**
    * 标准化日志输出
    * @param message 日志信息
    */
  def printMessage(message:String): Unit ={
    println(sdf.format(new Date) + " | " + message)
  }



  /**
    * hive表库路径和表路径格式化
    * @param tableName 表名(小写)
    * @return
    */
  def generateDir(tableName:String):String = {
    val table_message = tableName.split("\\.")
    table_message(0)+".db/"+table_message(1)  //拼接hdfs库名和表名
  }
}
