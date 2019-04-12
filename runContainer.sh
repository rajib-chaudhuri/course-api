#!/bin/sh
applicationName=$1
docker run --name  $applicationName'Container -p 80:8090 'suswan/'$applicationName