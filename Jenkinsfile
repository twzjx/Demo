pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
            withMaven(maven:'apache-maven-3.8.6') {
                sh 'mvn -B -DskipTests clean install'
                }
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
