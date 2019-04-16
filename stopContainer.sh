#!/bin/sh
applicationName=$1
echo "applicationName---->>$applicationName"
if(docker stop "$applicationName"'Container' >/dev/null 2>&1); then
	echo "$applicationName docker container stopped"
else
	echo "docker container cannot be stopped  ---not exist"
fi

if(docker rm $applicationName'Container' >/dev/null 2>&1); then
	echo "$applicationName docker container removed"	
else
	echo "docker container could not be removed ---not exist"	 
fi
