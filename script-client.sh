#!/bin/bash
# Reset
OFF='\033[0m'       # Text Reset

# Regular Colors
Black='\033[0;30m'        # Black
Red='\033[0;31m'          # Red
Green='\033[0;32m'        # Green
Yellow='\033[0;33m'       # Yellow
Blue='\033[0;34m'         # Blue
Purple='\033[0;35m'       # Purple
Cyan='\033[0;36m'         # Cyan
White='\033[0;37m'        # White

# Bold
BBlack='\033[1;30m'       # Black
BRed='\033[1;31m'         # Red
BGreen='\033[1;32m'       # Green
BYellow='\033[1;33m'      # Yellow
BBlue='\033[1;34m'        # Blue
BPurple='\033[1;35m'      # Purple
BCyan='\033[1;36m'        # Cyan
BWhite='\033[1;37m'       # White


CONTAINER_NAME="hangman-client"
IMAGE_NAME="hangman-client-image"
DOCKERFILE_NAME="Dockerfile-client"


echo -e "${BGreen}===> REMOVE CONTAINERS <===${OFF}"
echo -e "${Blude}removing $CONTAINER_NAME${OFF}"
docker rm -f $CONTAINER_NAME
echo -e "${BGreen}===> BUILD DOCKER IMAGE <==="
docker build -f $DOCKERFILE_NAME -t $IMAGE_NAME .

echo -e "${BGreen}===> RUN THIS COMMAND <====${OFF}"
echo -e "${BBlue}docker run -dit --name ${CONTAINER_NAME} --link hangman-server:server ${IMAGE_NAME} ${OFF}"
