docker build -t hangman-server-image .
docker run -dit -p 1000-65535:1000-65535 --name hangman-server hangman-server-image
