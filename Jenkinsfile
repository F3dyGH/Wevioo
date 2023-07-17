pipeline {
    agent any

    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.33.10:8081"
        NEXUS_REPOSITORY = "maven-releases"
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

        /* stage("SonarQube"){
            steps{
                sh "mvn sonar:sonar \
                      -Dsonar.projectKey=Wevioo-backend \
                      -Dsonar.host.url=http://192.168.33.10:9000 \
                      -Dsonar.login=254c084fc103c43f4b2c14ad52eb378f5408f455"
            }
        } */

        /* stage("Nexus Deploy") {
            steps {
                script {
                         pom = readMavenPom file: "pom.xml";
                         filesByGlob = findFiles(glob: "target *//*.${pom.packaging}");
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
                                credentialsId: "nexusPwd",
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
        } */
          stage("publish to nexus") {
                    steps {
                        script {
                            // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                            pom = readMavenPom file: "pom.xml";
                            // Find built artifact under target folder
                            filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                            // Print some info from the artifact found
                            echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                            // Extract the path from the File found
                            artifactPath = filesByGlob[0].path;
                            // Assign to a boolean response verifying If the artifact name exists
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
                                        // Artifact generated such as .jar, .ear and .war files.
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],

                                        // Lets upload the pom.xml file for additional information for Transitive dependencies
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
    }
}
