#!/bin/sh
applicationName=$1
docker run --name  courseapiContainer -p 80:8090 suswan/courseapi