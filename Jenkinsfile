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

        stage('Stop old + Start new') {
            steps {
                sh '''
                    echo "=== Starting deployment ==="

                    # Try to kill old running java app 
                    pkill -f "java.*java-demo-app" || echo "No old app was running"

                    # Small wait so port becomes free
                    sleep 3

                    # Start the new jar
                    java -jar /var/lib/jenkins/workspace/java-demo-pipeline/target/java-demo-app-1.0.jar >> app.log 2>&1
                    echo "=== App should be running now ==="
                    echo "Check log with:   cat $WORKSPACE/app.log"
                    echo "Check process with:   ps aux | grep java"
                '''
            }
        }


    }
}
