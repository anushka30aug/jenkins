pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sh 'nohup java -jar target/java-demo-app-1.0.jar > app.log 2>&1 &'
            }
        }
    }
}
