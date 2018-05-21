node {
    def mvnHome = tool "Maven"

    stage("Checkout") {
        checkout scm
    }
    stage("Unit tests") {
        sh "${mvnHome}/bin/mvn clean test"
    }
}