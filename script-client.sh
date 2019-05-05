docker build -f Dockerfile-client -t hangman-client-image .
docker run -dit --name hangman-client --link hangman-server:server hangman-client-image
