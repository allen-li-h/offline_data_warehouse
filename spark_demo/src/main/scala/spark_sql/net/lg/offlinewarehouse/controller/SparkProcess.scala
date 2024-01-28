package spark_sql.net.lg.offlinewarehouse.controller

import spark_sql.net.lg.offlinewarehouse.service.SparkEtlModeService

/**
  * @Author ：Allen
  * @Project ：bigdata_demo
  * @Name ：SparkProcess
  * @Date ：2024年01月26日 0026 19:15:56
  * @Info : 程序的主入口，判断参数是否异常
  */
class SparkProcess(args:Array[String]) {
  assert(args.length < 6, s"Parameter over limit 5, now is ${args.length}")
  def executeSparkEtlModelService(): Unit = {
    SparkEtlModeService.sparkEtlModeService(args)
  }
}


object SparkProcess {
  def main(args: Array[String]): Unit = {

    val arg:Array[String] = Array("sqoop_merge_table","/user/hive/warehouse/ods.db/ods_dim_edw_dim_employee/all","ods.ods_dim_edw_dim_employee","uid")

    assert(arg.length > 0, "Not Enough arguments: arguments must be greater then 0.")
    val sparkProcess = new SparkProcess(arg)
    sparkProcess.executeSparkEtlModelService()
  }
}