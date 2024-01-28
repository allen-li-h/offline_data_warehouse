package spark_sql.net.lg.offlinewarehouse.service

import spark_sql.net.lg.offlinewarehouse.dao.{SparkEtlInsertDao, SparkEtlMergeDao}

/**
  * @Author ：Allen
  * @Project ：bigdata_demo
  * @Name ：SparkEtlModeService
  * @Date ：2024年01月26日 0026 21:25:44
  * @Info ：通过参数解析spark作业模式
  */
object SparkEtlModeService {

  def sparkEtlModeService(args:Array[String]): Unit = {
    args match {
      case Array("sqoop_merge_table", srcPath, tgtTableName, keyCol) => SparkEtlMergeDao.executeMergeMode("fileMode", srcPath, tgtTableName, keyCol)
      case Array("insert_overwrite_table", sql, tgtTableName) => SparkEtlInsertDao.executeInsertMode(sql, tgtTableName, "0")
      case Array("insert_into_table", sql, tgtTableName) => SparkEtlInsertDao.executeInsertMode(sql, tgtTableName, "1")
      case _ => throw new Error(s"Arguments is not match: ${args.mkString(" ")}")
    }
  }

}
