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
                sh "mvn sonar:sonar \
                      -Dsonar.projectKey=Wevioo-backend \
                      -Dsonar.host.url=http://192.168.33.10:9000 \
                      -Dsonar.login=254c084fc103c43f4b2c14ad52eb378f5408f455"
            }
        }

        stage("Publish to nexus") {
            steps {
                script {
                         pom = readMavenPom file: "pom.xml";
                         filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                         artifactPath = filesByGlob[0].path;
                         artifactExists = fileExists artifactPath;

                           if(artifactExists) {

                              nexusArtifactUploader(
                                nexusVersion: "nexus3",
                                protocol: "http",
                                nexusUrl: "192.168.33.10:8081",
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: "maven-releases",
                                credentialsId: "83b06e01-00c6-42a8-a5f6-d9f9b7b7bc39",
                                artifacts: [
                                   [
                                     artifactId: pom.artifactId,
                                     classifier: '',
                                     file: artifactPath,
                                     type: pom.packaging
                                   ],

                                   [
                                     artifactId: pom.artifactId,
                                     classifier: '',
                                     file: "pom.xml",
                                     type: "pom"
                                   ]
                                ]
                              );

                           } else {
                                error "*** File could not be found";
                           }
                }
            }
        }
    }
}
