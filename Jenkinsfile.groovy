node {
    try {
        stage("Checkout") {
            checkout scm
        }
        stage("Unit tests") {
            sh "docker run -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn clean test"
        }
        stage("Integration tests") {
            sh "docker run -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn clean test-compile failsafe:integration-test"
        }
        stage("Build artifact") {
            sh "docker run -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn clean package"
        }
        stage("UAT") {
            sh "java -jar -Dhttp.server.port=8082 target/zip-service-jar-with-dependencies.jar &"
            sh "sleep 5"
            sh "docker run --net host -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn -Dtest=\"*UAT\" -Dhost=host.docker.internal -Dport=8082 test"
        }
        stage("Store Artifact") {
            archiveArtifacts artifacts: 'target/zip-service-jar-with-dependencies.jar', fingerprint: true
        }
    } finally {
        junit 'target/surefire-reports/**/*.xml'
        junit 'target/failsafe-reports/**/*.xml'
        step([$class: 'JacocoPublisher'])
    }
}
