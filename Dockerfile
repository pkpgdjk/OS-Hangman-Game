FROM openjdk:12
COPY . /usr/src/hangman-server/src/com/hangman/
WORKDIR /usr/src/hangman-server/src/com/hangman/
RUN pwd && ls
RUN javac -encoding UTF-8 *.java
CMD ["java", "Server"]
