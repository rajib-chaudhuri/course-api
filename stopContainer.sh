#!/bin/sh
applicationName=$1
if(docker stop $applicationName'Container' >/dev/null 2>&1); then
	echo "$applicationName docker container stopped"	
else
	echo "not exist"	
fi