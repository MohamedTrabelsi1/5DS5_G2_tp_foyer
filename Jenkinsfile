pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = "mehdibedoui/alpine"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }
    
    stages {
        stage('Récupération du code') {
            steps {
                git branch: 'MehdiBedoui_5DS5_G2',
                url: 'https://github.com/MohamedTrabelsi1/5DS5_G2_tp_foyer'
            }
        }

        stage('Nettoyage') {
            steps {
                sh 'mvn clean'
            }
        }
    stage('compiler') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('test') {
                    steps {
                        sh 'mvn test'
                    }
                }
}
}