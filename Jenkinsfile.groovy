node {
    def mvnHome = tool "Maven"

    stage("Checkout") {
        checkout scm
    }

    try {
        stage("Unit tests") {
            sh "${mvnHome}/bin/mvn clean test"
        }
        stage("Integration tests") {
            sh "${mvnHome}/bin/mvn test-compile failsafe:integration-test"
        }
        stage("Build artifact") {
            sh "${mvnHome}/bin/mvn package"
            sh "docker build -t pdincau/zip-service:${env.BUILD_NUMBER} ."
        }
        stage("UAT") {
            sh "java -jar -Dhttp.server.port=8082 target/zip-service-jar-with-dependencies.jar &"
            sh "sleep 5"
            sh "${mvnHome}/bin/mvn -Dtest=\"*UAT\" -Dhost=localhost -Dport=8082 test"
        }
        stage("Store artifact") {
            archiveArtifacts artifacts: 'target/zip-service-jar-with-dependencies.jar', fingerprint: true
        }
    } finally {
            junit 'target/surefire-reports/**/*.xml'
            junit 'target/failsafe-reports/**/*.xml'
            step([$class: 'JacocoPublisher'])
    }
}
