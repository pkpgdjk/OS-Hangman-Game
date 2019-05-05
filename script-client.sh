#!/bin/bash
CONTAINER_NAME="hangman-client"
IMAGE_NAME="hangman-client-image"
DOCKERFILE_NAME="Dockerfile-client"


ECHO "===> REMOVE CONTAINERS <===="
echo "removing $CONTAINER_NAME"
docker rm -f $CONTAINER_NAME
ECHO "===> BUILD DOCKER IMAGE <===="
docker build -f $DOCKERFILE_NAME -t $IMAGE_NAME .

echo "RUN THIS COMMAND"
echo "docker run -dit --name $CONTAINER_NAME --link hangman-server:server $IMAGE_NAME"
