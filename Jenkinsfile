pipeline {
    agent {
            docker {
                image 'maven:3-jdk-11'
                args '-v /root/.m2:/root/.m2'
            }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage("Copy the created .jar file to home directory for deployment"){
            steps{
            fileOperations([fileCopyOperation(
                                flattenFiles: false,
                                includes: '*.jar',
                                targetLocation: "home/user"
                )])
            }
        }
    }
}
