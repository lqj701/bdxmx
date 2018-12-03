#!/bin/bash

PROJECT_PATH=`pwd`
PROJECT_NAME=callcenter

PID=`ps -ef | grep $PROJECT_PATH/$PROJECT_NAME | grep -v "grep" | awk '{print $2}'`

if [ $PID ]; then
    kill -9 $PID
    if [[ $? -eq 0 ]]; then
        echo "success to stop $PROJECT_NAME. pid: $PID"
    else
        echo "fail to stop $PROJECT_NAME. pid: $PID"
    fi
fi