#!/bin/sh
#

IMAGE_DB='postgres:alpine'
IMAGE_APP='com.gft.employee-appraisal:latest'

echo 'Launch new DB container based on' $IMAGE_DB
docker run \
    -e POSTGRES_USER='postgres' \
    -e POSTGRES_PASSWORD='postgres' \
    -e POSTGRES_DB='employeeappraisals' \
    -d \
    -p 5432:5432 \
    $IMAGE_DB

echo 'Allowing DB container to start by waiting 20s...'
sleep 20s

echo 'Launching new APP container based on image' $IMAGE_APP
docker run \
    -e SPRING_PROFILES_ACTIVE='default,dev,oauth2' \
    -d \
    -p 11004:11004 \
    $IMAGE_APP

echo 'Waiting 20s for DB and APP containers to start'
sleep 20s

echo 'DB Logs'
docker ps -a | grep "$IMAGE_DB" | awk '{print $1}' | xargs docker logs

echo 'APP Logs'
docker ps -a | grep "$IMAGE_APP" | awk '{print $1}' | xargs docker logs

echo 'Success'

#
# References:
#   - https://hub.docker.com/_/postgres/
#   - https://stackoverflow.com/questions/30494050/how-do-i-pass-environment-variables-to-docker-containers