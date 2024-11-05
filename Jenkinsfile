pipeline {
    agent any
    
    triggers {
        githubPush()
    }
    
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

        stage('Maven Clean') {
            steps {
                echo 'Nettoyage du Projet:'
                sh 'mvn clean package'
            }
        }

        stage('Maven Compile') {
            steps {
                echo 'Construction du Projet:'
                sh 'mvn compile'
            }
        }

        stage('Maven Test') {
            steps {
                echo 'Execution des Tests:'
                sh 'mvn test'
            }
        }
	
	stage('Nexus') {
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

        stage('Remove Old Docker Image') {
            steps {
                script {
                    sh 'docker rmi -f mehdibedoui/foyerspring:latest || true' // `-f` forces removal, and `|| true` ignores errors
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t mehdibedoui/foyerspring .'
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
	    steps {
		withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
		    sh 'echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin'
		    sh 'docker push mehdibedoui/foyerspring:latest'
		}
	    }
	}

        stage('Docker Compose Down') {
            steps {
                dir('firstpipeline') {  
                    sh 'docker compose down'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                dir('firstpipeline') {  
                    sh 'docker compose up -d'
                }
            }
        }
	// stage
        // stage('Final Docker Compose Down') { // Only include if shutdown is desired at the end
        //     steps {
        //         dir('firstpipeline') {  
        //             sh 'docker compose down'
        //         }
        //     }
        // }
    }
}

