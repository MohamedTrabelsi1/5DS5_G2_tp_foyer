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
        
        stage('Construction') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        
        stage('Construction Image Docker') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }
        
        stage('Push Image Docker') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push("latest")
                    }
                }
            }
        }
        
        stage('Déploiement') {
            steps {
                echo "Déploiement de l'image ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }
    }
    
    post {
        always {
            sh 'docker logout'
        }
    }
}
