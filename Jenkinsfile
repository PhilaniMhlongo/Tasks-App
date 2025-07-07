#!/usr/bin/env groovy

pipeline {   
    agent any
    stages {
        stage("test") {
            steps {
                script {
                    echo "Testing the application..."
                    

                }
            }
        }
        stage("package") {
            steps {
                script {
                    echo "Packaging the application to JAR file..."
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
