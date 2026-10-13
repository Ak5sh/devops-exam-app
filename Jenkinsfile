pipeline {
    agent any

    environment {
        DOCKER_HUB_USER = 'akashv2310'
        IMAGE_NAME      = 'devops-demo'
        REGISTRY_CRED   = 'dockerhub-credentials'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test with Maven') {
            steps {
                sh 'mvn clean test package'
            }
        }

        stage('Docker Build & Tag') {
            steps {
                sh "docker build -t ${DOCKER_HUB_USER}/${IMAGE_NAME}:${BUILD_NUMBER} ."
                sh "docker tag ${DOCKER_HUB_USER}/${IMAGE_NAME}:${BUILD_NUMBER} ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
            }
        }

        stage('Push to Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${REGISTRY_CRED}", usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
                    sh 'echo $DH_PASS | docker login -u $DH_USER --password-stdin'
                    sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:${BUILD_NUMBER}"
                    sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
                sh "kubectl set image deployment/devops-demo-deployment devops-demo=${DOCKER_HUB_USER}/${IMAGE_NAME}:${BUILD_NUMBER}"
                sh 'kubectl rollout status deployment/devops-demo-deployment --timeout=60s'
            }
        }

        stage('Verify Deployment') {
            steps {
                sh 'kubectl get pods -l app=devops-demo'
                sh 'kubectl get svc devops-demo-service'
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
    }
}