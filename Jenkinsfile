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


  

        // stage('Stop old + Start new') {
        //     steps {
        //         sh '''
        //             echo "=== Starting deployment ==="

        //             # Try to kill old running java app 
        //             pkill -f "java.*java-demo-app" || echo "No old app was running"

        //             # Small wait so port becomes free
        //             sleep 3

        //             # Start the new jar
        //             /var/lib/jenkins/workspace/java-demo-pipeline/target/java-demo-app-1.0.jar >> app.log 2>&1
        //             echo "=== App should be running now ==="
        //             echo "Check log with:   cat $WORKSPACE/app.log"
        //             echo "Check process with:   ps aux | grep java"
        //         '''
        //     }
        // }


        // stage('Deploy') {
        //     steps {
        //         sh '''
        //             echo "=== Running deploy script from server ==="
        //             /opt/java-demo-app/restart.sh
        //         '''
        //     }
        // }

        stage('Deploy') {
            steps {
                sh '''
                    echo "=== Starting deployment from pipeline ==="

                    JAR="$WORKSPACE/target/java-demo-app-1.0.jar"
                    LOG="/opt/java-demo-app/app.log"
                    PID_FILE="/opt/java-demo-app/app.pid"

                    # Prevent Jenkins from killing the process
                    export BUILD_ID=dontKillMe
                    export JENKINS_NODE_COOKIE=dontKillMe

                    # Stop old app if PID file exists
                    if [ -f "$PID_FILE" ]; then
                        PID=$(cat "$PID_FILE")

                        if ps -p $PID | grep -q java; then
                            echo "Stopping old app PID $PID"
                            kill $PID
                            sleep 5
                        fi

                        rm -f "$PID_FILE"
                    fi

                    # Extra safety
                    pkill -f "$JAR" || true

                    # Start new app detached
                    nohup java -jar "$JAR" >> "$LOG" 2>&1 &

                    echo $! > "$PID_FILE"

                    echo "New app started with PID $(cat $PID_FILE)"
                '''
            }
        }

    }
}
