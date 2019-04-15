#!/bin/sh
applicationName=$1
if(docker stop $applicationName'Container' >/dev/null 2>&1); then
	echo "$applicationName docker continaer stopped"	
else
	echo "not exist"	
fi