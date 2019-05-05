#!/bin/bash
CONTAINER_NAME="hangman-server"
IMAGE_NAME="hangman-server-image"
DOCKERFILE_NAME="Dockerfile-server"


ECHO "===> REMOVE CONTAINERS <===="
echo "removing $CONTAINER_NAME"
docker rm -f $CONTAINER_NAME
ECHO "===> BUILD DOCKER IMAGE <===="
docker build -f $DOCKERFILE_NAME -t $IMAGE_NAME .

echo "===> RUN DOCKER CONTAINER <==="
docker run -dit --name $CONTAINER_NAME $IMAGE_NAME
