pipeline {
    agent any
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
