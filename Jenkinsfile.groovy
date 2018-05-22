node {
    stage("Checkout") {
        checkout scm
    }
    stage("Unit tests") {
        try {
            sh "docker run -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn clean test"
        } finally {
            junit 'target/surefire-reports/**/*.xml'
        }
    }
    stage("Integration tests") {
        try {
            sh "docker run -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn clean test-compile failsafe:integration-test"
        } finally {
            junit 'target/failsafe-reports/**/*.xml'
        }
    }
    stage("Build artifact") {
        sh "docker run -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn clean package"
    }
    stage("UAT") {
        sh "java -jar -Dhttp.server.port=8082 target/zip-service-jar-with-dependencies.jar &"
        sh "sleep 5"
        try {
            sh "docker run --net host -v /tmp/m2:/root/.m2 -v \$(pwd):/app -w /app maven:3.5.3-jdk-8-alpine mvn -Dtest=\"*UAT\" -Dhost=host.docker.internal -Dport=8082 test"
        } finally {
            junit 'target/surefire-reports/**/*acceptance*.xml'
        }
    }
    stage("Store Artifact") {
        archiveArtifacts artifacts: 'target/zip-service-jar-with-dependencies.jar', fingerprint: true
    }
}
