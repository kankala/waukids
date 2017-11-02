#!/bin/sh

SERVICE_NAME=scanner
PID_PATH_NAME=/tmp/scanner-pid

if [ -f $PID_PATH_NAME ]; then
	PID=$(cat $PID_PATH_NAME);
	echo "$SERVICE_NAME stoping ..."
	kill $PID

	echo "$SERVICE_NAME stopped ..."
	rm $PID_PATH_NAME
else
	echo "$SERVICE_NAME is not running ..."
fi
