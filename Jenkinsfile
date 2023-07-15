pipeline {
    agent any

    stages{
        stage('Git'){
            steps {
                git branch: 'main', url:'https://github.com/F3dyGH/Wevioo.git',
                credentialsId : 'password'
            }
        }
        stage('Maven Package'){
            steps {
                 sh " mvn -version "
                 sh " java -version "
                 sh " mvn package -e "
            }
        }

        stage("Maven Clean"){
            steps {
                sh " mvn clean -e "
            }
        }

        stage("Maven Compile"){
            steps {
                sh " mvn compile -e "
            }
        }

        stage("Maven Install"){
            steps {
                sh " mvn install "
            }
        }

        stage ('Unit Test') {
             steps{
                 sh " mvn  test "
             }
        }

        stage("SonarQube"){
            steps{
                sh " mvn sonar:sonar \
                    -Dsonar.projectKey=Wevioo-backend \
                    -Dsonar.host.url=http://192.168.33.10:9000 \
                    -Dsonar.login=1af186509f8912701b4d50e4325ed265f646c699"
            }
        }
    }
}
