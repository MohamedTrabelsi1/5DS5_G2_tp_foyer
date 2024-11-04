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
                sh 'mvn clean package'
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
        
        stage('nexus') {
            steps {
                echo 'build: '
                sh 'mvn clean deploy -DskipTests'
            }
        }
        
        stage('SonarQube') {
            steps {
                echo 'Analyse de la Qualité du Code : '
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube13#'
            }
        }

        stage('Docker Compose') {
            steps {
                echo 'Running Docker Compose'
                sh 'docker-compose up -d --build'
            }
        }
    }
}

