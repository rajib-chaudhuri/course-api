#!/bin/sh

if(docker rm CourseApiContainer >/dev/null 2>&1); then
	echo "exist"
else
	echo "not exist"	
fi
