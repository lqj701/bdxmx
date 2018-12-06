#!/bin/bash

PROJECT_PATH=`pwd`
PROJECT_NAME=miniprogram
VERSION=1.0.0

PID=`ps -ef | grep $PROJECT_PATH/$PROJECT_NAME | grep -v "grep" | awk '{print $2}'`

if [ $PID ]; then
    kill -9 $PID
    echo "kill $PROJECT_NAME pid: $PID"
fi

nohup java -jar $PROJECT_PATH/$PROJECT_NAME-$VERSION-SNAPSHOT.jar > /dev/null 2>&1 &

PID=`ps -ef | grep $PROJECT_PATH/$PROJECT_NAME | grep -v "grep" | awk '{print $2}'`
echo "$PROJECT_NAME restarted! pid: $PID"