node {
    def mvnHome = tool "Maven"
    def app

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
            app = docker.build("pdincau/zip-service")
        }
        stage("UAT") {
            docker.image('pdincau/zip-service').withRun('-p 8082:8080') { c ->
                sh "sleep 5"
                sh "${mvnHome}/bin/mvn -Dtest=\"*UAT\" -Dhost=localhost -Dport=8082 test"
            }
        }
        stage("Store artifact") {
            archiveArtifacts artifacts: 'target/zip-service-jar-with-dependencies.jar', fingerprint: true
            app.push("${env.BUILD_NUMBER}")
        }
    } finally {
            junit 'target/surefire-reports/**/*.xml'
            junit 'target/failsafe-reports/**/*.xml'
            step([$class: 'JacocoPublisher'])
    }
}
