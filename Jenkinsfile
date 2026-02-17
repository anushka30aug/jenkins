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
                sh '''
                set -x
                echo "Workspace is $WORKSPACE"
                ls -l $WORKSPACE/target
                export BUILD_ID=dontKillMe
                nohup java -jar $WORKSPACE/target/java-demo-app-1.0.jar > $WORKSPACE/app.log 2>&1 &
                sleep 5
                ps -ef | grep java
                '''
            }
        }


    }
}
