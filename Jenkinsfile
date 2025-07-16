#!/usr/bin/env groovy

pipeline {   
    agent any
    tools {
        maven 'maven3.9'
    }
    stages {

        stage("version increase") {
            steps {
                script {
                    echo 'increasing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.VERSION = "$version"
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
                   echo "building the docker image..."
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                        sh "docker build -t philanimhlongo/task-app:${VERSION} ."
                        sh 'echo $PASS | docker login -u $USER --password-stdin'
                        sh "docker push philanimhlongo/task-app:${VERSION}"
                }
            }
        } 


        stage("Commit changes"){
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      
                    
                    echo "Committing changes to the repository..."
                    sh "git config --global user.name 'jenkins'"
                    sh "git config --global user.email 'jenkins@devops.com'"
                    sh "git status"
                    sh "git branch"
                    sh "git config --list"
                    sh "git remote set-url origin https://${USERNAME}:${PASSWORD}@github.com/PhilaniMhlongo/Tasks-App.git"
                    sh "git add ."
                    sh "git commit -m 'Version updated to 1.0.${VERSION}'"
                    sh "git push origin HEAD:main"
                    sh "git config user.email 'jenkins@devops.com'"
                }
            }
            }
        }
                
    }
} 
