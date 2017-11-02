#!/bin/sh

SERVICE_NAME=scanner
JAVA_PATH=./jre
PATH_TO_JAR=./lib/scanner-1.0.0.jar
PID_PATH_NAME=/tmp/scanner-pid
JAVA_OPTS="-server -XX:+UseG1GC"
JAVA_OPTS+=" -Dspring.config.location=file:./config/application.yml -Dlogging.config=./config/logback.xml"

echo "Starting $SERVICE_NAME ..."

if [ ! -f $PID_PATH_NAME ]; then
	nohup $JAVA_PATH/bin/java $JAVA_OPTS -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
		echo $! > $PID_PATH_NAME
	echo "$SERVICE_NAME started ..."
else
	echo "$SERVICE_NAME is already running ..."
fi
