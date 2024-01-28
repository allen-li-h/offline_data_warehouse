#!/bin/bash

#################################################################
# 脚本名称：sqoop_edw_dim_employee.sh
# 脚本功能：创建sqoop迁移员工信息任务
# 脚本参数：无
# 调用形式：
# 编写人：
# 编写日期：
# 修改记录：
#################################################################


# 获取公用配置文件
source ~/function/Proc_Function.sh
source ${work_home}/function/Sqoop_Tool.sh


# 获取参数信息
run_info="$@"
sh_name=`echo $0|xargs basename`
now_data=`date "+%Y%m%d"`
mkdir -p ${work_home}/logs/${now_data}

## 使用用例
#Usage() {
#    echo "脚本名称：${sh_name}"
#    echo "脚本功能：实现sqoop员工信息表迁移"
#    echo "脚本调用：sh sqoop_edw_dim_employee.sh -b begin_date -e end_date"
#    echo "参数说明："
#    echo "使用案例："
#    exit 1
#}
#
#
## 判断是否参数异常
#if [ $# -lt 2 ]
#then
#    echo "调用${run_info}错误！$#"
#    echo "详情请参考如下："
#    Usage
#fi


job_name="edw_dim_company"
src_owner="dim_db"
query_import="select uid,username,sex,idcard,employmentdate,comcode,date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time,date_format(update_time,'%Y-%m-%d %H:%i:%s') as update_time,dml_type from edw_dim_employee where update_time >= date_format('${start_date}','%Y-%m-%d %H:%i:%s') and update_time <= date_format('${end_date}','%Y-%m-%d %H:%i:%s') and \$CONDITIONS"
tgt_dir="/user/hive/warehouse/ods.db/ods_dim_edw_dim_employee/all"
key_col="comcode4"
tgt_table="ods.ods_dim_edw_dim_employee"
split_by=""
map_tasks="1"
mapping_var=""
logfile="${work_home}/logs/${now_data}/sqoop_edw_dim_company.$$.log"


Create_SqoopJob "${job_name}" "${src_owner}" "${query_import}" "${tgt_dir}" "${split_by}" "${map_tasks}" "${mapping_var}" "${logfile}"