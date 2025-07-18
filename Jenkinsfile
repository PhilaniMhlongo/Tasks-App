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
<<<<<<< HEAD
                    echo 'Increasing app version...'
                    
             
                    def currentVersion = sh(
                        script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
                        returnStdout: true
                    ).trim()
                    echo "Current version: ${currentVersion}"
                    
                   
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    
                   
                    def newVersion = sh(
                        script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
                        returnStdout: true
                    ).trim()
                    
                    env.VERSION = newVersion
                    echo "Version updated to: ${env.VERSION}"
=======
                    echo 'increasing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.VERSION = "$version"
>>>>>>> 5a0abf8 (chore i did not push)
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
<<<<<<< HEAD
                    echo "Building Docker image with tag: ${env.VERSION}"
                    withCredentials([usernamePassword(credentialsId: 'Docker', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh "docker build -t philanimhlongo/task-app:${env.VERSION} ."
                        sh 'echo $PASS | docker login -u $USER --password-stdin'
                        sh "docker push philanimhlongo/task-app:${env.VERSION}"
                    }
                }
            }
        }

        stage("commit changes") {
            steps {
                script {
                    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                        echo "Committing changes to the repository..."
                        sh "git config --global user.name 'jenkins'"
                        sh "git config --global user.email 'jenkins@devops.com'"
                        sh "git remote set-url origin https://PhilaniMhlongo:${GITHUB_TOKEN}@github.com/PhilaniMhlongo/Tasks-App.git"
                        sh "git add ."
                        sh "git commit -m 'Version updated to ${env.VERSION}' || echo 'No changes to commit'"
                        sh "git push origin HEAD:main"
                    }
                }
            }
        }

    }
}
=======
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
}
 
>>>>>>> 5a0abf8 (chore i did not push)
