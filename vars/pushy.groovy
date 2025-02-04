// push.groovy
def call(String Project, String ImageTag, String dockerhubuser) {
    // Use Jenkins credentials to log into DockerHub
    withCredentials([usernamePassword(credentialsId: 'dockerHubCred', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        // Log in to DockerHub
        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
    }
    
    // Push the Docker image to DockerHub
    sh "docker push ${dockerhubuser}/${Project}:${ImageTag}"
}
