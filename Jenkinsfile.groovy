node {
    def mvnHome = tool "Maven"

    stage("Checkout") {
        checkout scm
    }
    stage("Unit tests") {
        sh "${mvnHome}/bin/mvn clean test"
    }
    stage("Integration tests") {
        sh "${mvnHome}/bin/mvn clean test-compile failsafe:integration-test"
    }
    stage("Build artifact") {
        sh "${mvnHome}/bin/mvn clean package"
        sh "ls -al"
    }
}