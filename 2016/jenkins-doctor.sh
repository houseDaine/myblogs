#!/bin/bash 
 
APP_PARAMS="choice-doctor" 
APP_PID=`ps aux | grep "$APP_PARAMS" | grep -v grep | awk '{ print $2}'` 
for i in $APP_PID; do 
    echo "Kill PID [ $APP_PID ] contains $APP_PARAMS" 
    kill -9 $APP_PID 
done 
 
cd ~/.jenkins/workspace/choice-doctor/code/backend/java 
mvn package -Dmaven.test.skip=true 
 
BUILD_ID=dontKillMe nohup java -jar choice-doctor-provider/target/choice-doctor-provider-0.0.1-SNAPSHOT.jar & 
sleep 1m 
BUILD_ID=dontKillMe nohup java -jar choice-doctor-consumer/target/choice-doctor-consumer-0.0.1-SNAPSHOT.jar &  
