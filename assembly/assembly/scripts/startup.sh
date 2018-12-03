#!/bin/bash

PROJECT_PATH=`pwd`
PROJECT_NAME=callcenter
VERSION=1.0.0

PID=`ps -ef | grep $PROJECT_PATH/$PROJECT_NAME | grep -v "grep" | awk '{print $2}'`

if [ $PID ]; then
    echo "$PROJECT_NAME is running now. pid: $PID"
    exit
fi

nohup java -jar $PROJECT_PATH/$PROJECT_NAME-$VERSION-SNAPSHOT.jar  > /dev/null 2>&1 &

PID=`ps -ef | grep $PROJECT_PATH/$PROJECT_NAME | grep -v "grep" | awk '{print $2}'`

echo "$PROJECT_NAME started! pid: $PID"