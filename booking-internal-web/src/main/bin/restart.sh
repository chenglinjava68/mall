#!/bin/sh

ps -ef | grep 'booking-internal-web-0.0.1.jar' | grep -v grep |awk '{print $2}'|xargs kill 

sleep 5

ps -ef | grep 'booking-internal-web-0.0.1.jar' | grep -v grep |awk '{print $2}'|xargs kill -9

sleep 3

log_date=`date +%Y-%m-%d`
stdout_log="/data/logs/plateno_mall_booking/plateno_mall_booking_web_stdout.$log_date.log"

nohup java  -XX:+HeapDumpOnOutOfMemoryError -server -d64 -XX:PermSize=200m -XX:MaxPermSize=200m -Xmx1024m -Xms1024m -Xss1m -Xmn350m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseParallelOldGC -XX:MaxGCPauseMillis=200 -jar ./lib/booking-internal-web-0.0.1.jar > $stdout_log 2>&1 &
