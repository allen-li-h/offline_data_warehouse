#!/bin/bash

#################################################################
# 脚本名称：Sqoop_Tool.sh
# 脚本功能：创建sqoop迁移任务
# 脚本参数：
# 调用形式：
# 编写人：
# 编写日期：
# 修改记录：
#################################################################


# 获取公用配置文件
source ~/function/Proc_Function.sh


#################################################################
# 功能：获取运行环境
# 参数：src_base：dim_db, policy_db
# 示例：GetEnv "dim_db"
# 返回值：无
#################################################################
GetEnv() {
    local src_base
    src_base=$1
    if [ "${src_base}" == "dim_db" ]
    then
        source ${work_home}/conf/sqoop/mysql_dim_db.conf
    else
        source ${work_home}/conf/sqoop/mysql_policy_db.conf
    fi

}


#################################################################
# 功能：生成sqoop job
# 参数：job_name：job名称, src_owner：源系统库，query_import：查询语句，tgt_dir目标路径
#       split_by：拆分字段，map_tasks任务数，mapping_var：字段类型映射， logfile：日志文件路径
# 示例：getEnv "dim_db"
# 返回值：无
#################################################################
Create_SqoopJob() {
    local job_name src_owner query_import tgt_dir split_by map_tasks mapping_var logfile
    job_name=$1
    src_owner=$2
    query_import=$3
    tgt_dir=$4
    split_by=$5
    map_tasks=$6
    mapping_var=$7
    logfile=$8

    Std_PrintLog "INFO" "开始创建sqoop迁移任务${job_name}..." ${logfile}
    GetEnv "${src_owner}"
    if [ "${mapping_var}X" == "X" ]
    then
        #--direct
        #--null-none-string '\\N' \
        sqoop import \
              --connect ${connection_url} \
              --username ${username} \
              --password ${password} \
              --query "${query_import}" \
              --target-dir ${tgt_dir} \
              --split-by "${split_by}" \
              --as-parquetfile \
              --m ${map_tasks} \
              --null-string '\\N' \
              --mapreduce-job-name "sqoop_${job_name}" \
              --outdir ${work_home}/work/sqoop_import/ \
              --fetch-size 4000 \
              --delete-target-dir >> "${logfile}" 2>&1
        status=$?
    else
        sqoop import \
              --connect ${connection_url} \
              --username ${username} \
              --password ${password} \
              --query "${query_import}" \
              --target-dir ${tgt_dir} \
              --split-by "${split_by}" \
              --as-parquetfile \
              --m ${map_tasks} \
              --null-string '\\N' \
              --mapreduce-job-name "sqoop_${job_name}" \
              --outdir ${work_home}/work/sqoop_import/ \
              --map-column-java ${mapping_var} \
              --fetch-size 4000 \
              --delete-target-dir >> "${logfile}" 2>&1
        status=$?
    fi
    wait
    success=`cat "${logfile}"| grep -E "completed successfully"`

    # 判断创建任务执行状态
    if [ ${status} -ne 0 ] || [ "${success}X" == "X" ]
    then
        Std_PrintLog "ERROR" "数据迁移任务${job_name}执行失败" ${logfile}
        echo "日志文件为：${logfile}"
        Exit_Info "${logfile}"
    else
        echo "日志文件为：${logfile}"
        Std_PrintLog "INFO" "数据迁移任务${job_name}执行成功" ${logfile}
    fi
}



#################################################################
# 功能：执行数据合并任务
# 参数：src_base：dim_db, policy_db
# 示例：Run_SparkJob "dim_db"
# 返回值：无
#################################################################
Run_SparkJob() {
    local job_name key_col src_path tgt_table logfile
    job_name=$1
    key_col=$2
    src_path=$3
    tgt_table=$4
    logfile=$5

    spark-submit --master yarn \
                 --deploy-mode cluster \
                 --name "sqoop_${job_name}" \
                 --conf spark.sql.autoBroadcastJoinThreshold=400000000 \
                 --conf spark.yarn.executor.memoryOverhead=256M \
                 --conf spark.network.timeout=300 \
                 --conf spark.default.parallelism=500 \
                 --conf spark.sql.shuffle.partitions=500 \
                 --driver-memory 256M \
                 --executor-memory 512M \
                 --num-executors 2 \
                 --executor-cores 1 \
                 --class spark_sql.net.lg.offlinewarehouse.controller.SparkProcess \
                 ${work_home}/jars/spark/SaprkMergeETL.jar "sqoop_merge_table" "${src_path}" "${tgt_table}" "${key_col}"  >> ${logfile} 2>&1
    status=$?
    wait
    if [ ${status} -eq 0 ]
    then
        echo "执行成功"
    else
        echo "执行失败"
    fi
}

