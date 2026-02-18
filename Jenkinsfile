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

        // stage('Stop old + Start new') {
        //     steps {
        //         sh '''
        //             echo "=== Starting deployment ==="

        //             # Kill old app
        //             pkill -f "java.*java-demo-app" || echo "No old app was running"

        //             sleep 3

        //             # Prevent Jenkins from killing process
        //             export BUILD_ID=dontKillMe

        //             nohup java -jar /var/lib/jenkins/workspace/java-demo-pipeline/target/java-demo-app-1.0.jar >> app.log 2>&1 &

        //             echo "=== App started in background ==="
        //             echo "PID: $!"
        //         '''
        //     }
        // }
        
        stage('Deploy') {
            steps {
                sh '''
                    echo "=== Running deploy script from server ==="
                    /opt/java-demo-app/restart.sh
                '''
            }
        }


    }
}
