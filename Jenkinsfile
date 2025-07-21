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
