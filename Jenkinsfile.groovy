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
    }
    stage("UAT") {
        sh "java -jar -Dhttp.server.port=8082 target/zip-service-jar-with-dependencies.jar &"
        sh "sleep 5"
        sh "${mvnHome}/bin/mvn -Dtest=\"*UAT\" -Dhost=localhost -Dport=8082 test"
    }
}
