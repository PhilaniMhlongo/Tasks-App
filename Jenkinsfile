#!/usr/bin/env groovy

pipeline {   
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("test") {
            steps {
                script {
                    echo "Testing the application..."
                    sh "mvn test"
                    

                }
            }
        }
        stage("package") {
            steps {
                script {
                    echo "Packaging the application to JAR file..."
                    sh "mvn clean package"
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
