#!/bin/sh
applicationName=$1
docker run --name  courseapiContainer -d -p 80:8090 suswan/courseapi  