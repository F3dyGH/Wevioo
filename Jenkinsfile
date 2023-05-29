pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout your source code from version control
                // Replace the URL and credentials with your own
                git 'https://github.com/your-repo.git'
            }
        }

        stage('Build') {
            steps {
                // Set up JDK
                // Replace 'jdk8' with your desired JDK version
                tools {
                    jdk 'jdk8'
                }
                
                // Build your Maven project
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Package the application
                sh 'mvn package'
            }
        }
        }
    }
}
