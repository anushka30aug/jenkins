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

// THIS STAGE OF DEPLOY WORKING WELL WITH THE SCRIPT ATTACHED TO IT WHERE JAR FILE IS STARTING FROM THE SCRIPT
        // stage('Deploy') {
        //     steps {
        //         sh '''
        //             echo "=== Running deploy script from server ==="
        //             /opt/java-demo-app/restart.sh
        //         '''
        //     }
        // }


//THIS DEPLOY STAGE WORKS WELL WHEN WE DONT WANT TO ATTACH A SCRIPT AND DO DEPLOY + RUN JAR FILE FROM THE PIPELINE ONLY
        // stage('Deploy') {
        //     steps {
        //         sh '''
        //             echo "=== Starting deployment from pipeline ==="

        //             JAR="$WORKSPACE/target/java-demo-app-1.0.jar"
        //             LOG="/opt/java-demo-app/app.log"
        //             PID_FILE="/opt/java-demo-app/app.pid"

        //             # Prevent Jenkins from killing the process
        //             export BUILD_ID=dontKillMe
        //             export JENKINS_NODE_COOKIE=dontKillMe

        //             # Stop old app if PID file exists
        //             if [ -f "$PID_FILE" ]; then
        //                 PID=$(cat "$PID_FILE")

        //                 if ps -p $PID | grep -q java; then
        //                     echo "Stopping old app PID $PID"
        //                     kill $PID
        //                     sleep 5
        //                 fi

        //                 rm -f "$PID_FILE"
        //             fi

        //             # Extra safety
        //             pkill -f "$JAR" || true

        //             # Start new app detached
        //             nohup java -jar "$JAR" >> "$LOG" 2>&1 &

        //             echo $! > "$PID_FILE"

        //             echo "New app started with PID $(cat $PID_FILE)"
        //         '''
        //     }
        // }

// THIS IS TO DEPLOY APPLICATION IN DOCKER CONTAINER AND DEPLOY THE CONTAINER ON THE SAME SERVER AS THAT OF JENKINS
        // stage('Build Image') {
        //     steps {
        //         sh '''
        //             docker build -t java-demo-app:latest .
        //         '''
        //     }
        // }

        // stage('Deploy Container') {
        //     steps {
        //         sh '''
        //             echo "Stopping old container if exists..."
        //             docker rm -f java-demo-app || true

        //             echo "Starting new container..."
        //             docker run -d --name java-demo-app -p 8085:8085 java-demo-app:latest
        //         '''
        //     }
        // }


        stage('Build Image') {
            steps {
                sh '''
                    echo "Building Docker image..."
                    docker build -t anushkashukla003/java-demo-app:latest .
                '''
            }
        }

        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                        echo "Pushing image to Docker Hub..."
                        docker push anushkashukla003/java-demo-app:latest

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                    echo "Deploying on remote server..."

                    ssh -o StrictHostKeyChecking=no ssm-user@172.31.19.228 '
                        docker rm -f java-demo-app || true

                        docker pull anushkashukla003/java-demo-app:latest

                        docker run -d \
                        --name java-demo-app \
                        -p 9090:8085 \
                        anushkashukla003/java-demo-app:latest
                    '
                '''
            }
        }




    }
}
