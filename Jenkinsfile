pipeline {
    tools {
        maven "M3"
    }
    agent any
    stages {
       stage("Preparation") {
            steps {
                git 'https://github.com/jmanzaneque/Testing-practice.git'
            }
       }
       stage("Test") {
          steps {
            script {
                if(isUnix()) {
                    sh "cd tic-tac-toe-enunciado ; mvn test"
                } else {
                    bat(/${mvnHome}\bin\mvn -f tic-tac-toe-enunciado\pom.xml test/)
                }
            }
          }
        }
     }
     post {
         always {
            junit "tic-tac-toe-enunciado/**/target/surefire-reports/TEST-*.xml"
        }
     }
  }