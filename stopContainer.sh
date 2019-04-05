#!/bin/sh

if(docker stop CourseApiContainer >/dev/null 2>&1); then
	echo "docker continaer stopped"	
else
	echo "not exist"	
fi