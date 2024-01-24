#!/bin/bash

#################################################################
# 脚本名称：Sqoop_tool.sh
# 脚本功能：创建sqoop迁移任务
# 脚本参数：
# 调用形式：
# 编写人：
# 编写日期：
# 修改记录：
#################################################################


# 获取公用配置文件
source /home/dolphinscheduler/conf/sqoop/mysql_dim_db.conf
source /home/dolphinscheduler/function/Function.sh

# 获取参数信息
run_info="$@"
sh_name=`echo $0|xargs basename`


# 使用用例
Usage() {
    echo "脚本名称：${sh_name}"
    echo "脚本功能：实现sqoop数据迁移"
    echo "脚本调用：source Sqoop_tool.sh"
    echo "脚本调用：Create_SqoopJob"
    echo "参数说明："
    echo "使用案例："
    exit 1
}


# 判断是否参数异常
if [ $# -lt 5 ]
then
    echo "调用${run_info}错误！"
    echo "详情请参考如下："
    Usage
fi

Create_SqoopJob() {
    Std_PrintLog "INFO" "" ${logfile}
}

