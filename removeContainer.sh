#!/bin/sh

applicationName=$1
if(docker rm $applicationName'Container' >/dev/null 2>&1); then
	echo "$applicationName docker container removed"	
else
	echo "docker container could not be removed ---not exist"	
fi
