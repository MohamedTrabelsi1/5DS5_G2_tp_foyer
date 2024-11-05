pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                echo 'Pulling... ';
                git branch: 'AkremZaghdoudi_5DS5_G2',
                url: 'https://github.com/MohamedTrabelsi1/5DS5_G2_tp_foyer.git'
            }
        }
        
        stage('Testing Maven') {
            steps{
                sh 'mvn clean install'
            }
        }
        
        stage('SonarQube') {
            steps{
                echo 'Code Quality Analysis'
                //sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonar111111. -Dsonar.java.binaries=target/classes'
                withSonarQubeEnv('sonar') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar -Dsonar.host.url=http://192.168.2.138:9000'
                }
            }
        }
        
        stage('Mockito Tests') {
            steps {
                sh 'mvn test'  // Maven test, make sure Mockito is part of your dependencies
                sh 'mvn clean package'
            }
        }
        
        stage('Publish to Nexus') {
            steps {
                sh 'mvn clean deploy -Dusername=admin -Dpassword=nexus -DskipTests'
            }
        }
        
        
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image'
                sh 'mvn clean install'
                sh 'docker build -t tpfoyer:latest -f Dockerfile ./'
                echo "Docker image tpfoyer:latest built successfully"
            }
        }
        
        stage('Pushing to DockerHub') {
            steps {
                
                withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    sh "docker tag tpfoyer:latest akremzaghdoudi/tpfoyer:tpfoyer"
                    sh "docker push akremzaghdoudi/tpfoyer:tpfoyer"
                }
            }
        }
        stage('stoping docker compose') {
            steps {
                sh 'docker compose down'
            }
        }
        stage('Running docker compose') {
            steps {
                sh 'docker compose up -d'
            }
        }
        
        stage('Promethus & Grafana') {
            steps {
                    script {
                       sh 'docker start prometheus'
                       sh 'docker start grafana'
                    }
                }
            }
        
        
    
        
    }
}
