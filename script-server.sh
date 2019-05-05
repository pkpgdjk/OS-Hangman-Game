docker build -f Dockerfile-server -t hangman-server-image .
docker run -dit --name hangman-server hangman-server-image
