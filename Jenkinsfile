#!/usr/bin/env groovy
pipeline {
    agent any

    tools {
        maven 'maven3.9'
    }

    environment {
        VERSION = ''
    }

    stages {

        stage("version increase") {
            steps {
                script {
                    echo 'Increasing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.VERSION = version
                }
            }
        }

        stage("test") {
            steps {
                script {
                    echo "Testing the application..."
                    sh "mvn test -Dspring.profiles.active=test"
                }
            }
        }

        stage("package") {
            steps {
                script {
                    echo "Packaging the application to JAR file..."
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    echo "Building the Docker image..."
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh "docker build -t philanimhlongo/task-app:${VERSION} ."
                        sh 'echo $PASS | docker login -u $USER --password-stdin'
                        sh "docker push philanimhlongo/task-app:${VERSION}"
                    }
                }
            }
        }

        stage("commit changes") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        echo "Committing changes to the repository..."
                        sh "git config --global user.name 'jenkins'"
                        sh "git config --global user.email 'jenkins@devops.com'"
                        sh "git remote set-url origin https://${USERNAME}:${PASSWORD}@github.com/PhilaniMhlongo/Tasks-App.git"
                        sh "git add ."
                        sh "git commit -m 'Version updated to ${VERSION}' || echo 'No changes to commit'"
                        sh "git push origin HEAD:main"
                    }
                }
            }
        }

    }
}
