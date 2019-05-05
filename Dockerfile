FROM openjdk:12
COPY . /usr/src/hangman-server/src/com/hangman/
WORKDIR /usr/src/hangman-server/src/com/hangman/
RUN javac *.java
CMD ["java", "Server"]
