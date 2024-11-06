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

        stage('Maven install') {
            steps {
                echo 'Nettoyage du Projet:'
                sh 'mvn install'
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
	
        
     //   stage('SonarQube') {
      //      steps {
        //        echo 'Analyse de la Qualité du Code : '
          //      sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube13#'
          //  }
       // }

	//stage('Nexus') {
          //  steps {
            //    echo 'build: '
              //  sh 'mvn clean deploy -DskipTests'
           // }
      //  }
        
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

      //  stage('push docker image to docker hub') {
	//    steps {
	//	withcredentials([usernamepassword(credentialsid: 'docker-hub-credentials', usernamevariable: 'docker_username', passwordvariable: 'docker_password')]) {
	//	    sh 'echo "$docker_password" | docker login -u "$docker_username" --password-stdin'
	//	    sh 'docker push mehdibedoui/foyerspring:latest'
	//	}
	//    }
//	}

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

