node {
    try {
        def image
        def workspace = sh(returnStdout: true, script: 'pwd').trim()
        def m2 = "/tmp/m2"
        def version = "${env.BUILD_NUMBER}"

        stage("Checkout") {
            checkout scm
        }
        docker.image('maven:3.5.3-jdk-8-alpine').inside("-v ${m2}:/root/.m2 -v ${workspace}:/app -w /app") {
            stage("Unit Tests") {
                sh 'mvn clean test'
            }
            stage("Integration Tests") {
                sh "mvn test-compile failsafe:integration-test"
            }
            stage("Package to jar") {
                sh "mvn package"
            }
        }
        stage("Build Docker Image") {
            image = docker.build("pdincau/zip-service:$version")
        }
        stage("Test vs Container") {
            docker.image("pdincau/zip-service:$version").withRun() { c ->
                sleep 5
                docker.image('maven:3.5.3-jdk-8-alpine').inside("-v ${m2}:/root/.m2 -v ${workspace}:/app -w /app --link ${c.id}:app") {
                    sh "mvn -Dhost=app -Dport=8080 -Dtest=\"*UAT\" test"
                }
            }
        }
        stage("Push Image") {
            image.push(version)
            image.push("latest")
        }
    } finally {
        junit 'target/surefire-reports/**/*.xml'
        junit 'target/failsafe-reports/**/*.xml'
        step([$class: 'JacocoPublisher'])
    }
}
