#!/usr/bin/env groovy

pipeline {   
    agent any
    tools {
        maven 'maven3.9'
    }
    stages {
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
                    sh "mvn package -DskipTests"
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    echo "Building the image..."
                }
            }
        } 
                
    }
} 
