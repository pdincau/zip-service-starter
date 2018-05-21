node {
    stage("Checkout from GitHub") {
        checkout scm
        sh "ls -al"
    }
}