#!/bin/sh

ps -ef | grep 'booking-internal-job-0.0.1.jar' | grep -v grep |awk '{print $2}'|xargs kill -9 

sleep 5

log_date=`date +%Y-%m-%d`
stdout_log="/data/logs/plateno_mall_booking/plateno_mall_booking_job_stdout.$log_date.log"

nohup java -XX:+HeapDumpOnOutOfMemoryError -jar ./lib/booking-internal-job-0.0.1.jar > $stdout_log 2>&1 &
