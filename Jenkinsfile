pipeline {
    agent {
        docker {
            image 'openjdk:12'
            args '-p 8000:8000'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'java src/com/hangman/Server.java'
		sh 'echo this is server'
            }
        }
    }
}
