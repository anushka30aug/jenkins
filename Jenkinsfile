pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/anushka30aug/jenkins.git'
            }
        }

        stage('Build') {
            steps {
                dir('java-demo-app') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                dir('java-demo-app') {
                    sh '''
                    scp target/java-demo-app-1.0.jar ec2-user@APP-SERVER-IP:/home/ec2-user/
                    ssh ec2-user@APP-SERVER-IP "nohup java -jar java-demo-app-1.0.jar > app.log 2>&1 &"
                    '''
                }
            }
        }
    }
}
