pipeline {
    agent any

    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.33.10:8081"
        NEXUS_REPOSITORY = "maven-releases"
        NEXUS_USERNAME = "admin"
        NEXUS_PASSWORD = "admin"
    }

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

        stage("publish artifact to nexus") {
           steps {
               script {
                         pom = readMavenPom file: "pom.xml";
                         filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                         echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                         artifactPath = filesByGlob[0].path;
                         artifactExists = fileExists artifactPath;

                         if(artifactExists) {
                             echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                                nexusArtifactUploader(
                                    nexusVersion: NEXUS_VERSION,
                                    protocol: NEXUS_PROTOCOL,
                                    nexusUrl: NEXUS_URL,
                                    groupId: pom.groupId,
                                    version: pom.version,
                                    repository: NEXUS_REPOSITORY,
                                    credentialsId: "nexusPwd",
                                    artifacts: [
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],

                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: "pom.xml",
                                        type: "pom"]
                                    ]
                                );

                            } else {
                                error "*** File: ${artifactPath}, could not be found";
                            }
                        }
                    }
               }

           stage("Build Docker image") {
                steps {
                    script {
                                pom = readMavenPom file: "pom.xml";
                                sh "docker build -t app:${pom.version} ."
                                sh "docker tag app:${pom.version} 192.168.33.10:8082/repository/docker-images/app:${pom.version}"
                    }
                }
           }

           stage("Publish Docker image to Nexus") {
               steps {
                   script {
                                pom = readMavenPom file: "pom.xml";
                                sh "docker login -u admin -p admin 192.168.33.10:8082"
                                sh "docker push 192.168.33.10:8082/repository/docker-images/app:${pom.version}"
                   }
               }
           }

           stage("Extract Latest App Version") {
               steps {
                   script {
                    def tagsUrl = "${NEXUS_PROTOCOL}://${NEXUS_URL}/service/rest/v1/components?repository=docker-images&name=app"
                    def response = sh(returnStdout: true, script: "curl -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} ${tagsUrl}")
                    def tagsJson = readJSON text: response

                    def tags = tagsJson.items.collect { it.version.replace('app-', '') }
                    def latestVersion = tags.sort().reverse().head()

                    APP_VERSION = latestVersion

                    echo "Latest App Version: ${APP_VERSION}"
                    sh "sed -i 's/\${APP_VERSION}/${APP_VERSION}/g' docker-compose.yml"

                   }
               }
           }

        stage("Run Docker Compose") {
            steps {
                sh "docker-compose -f docker-compose.yml up -d"
            }
        }
    }
}
