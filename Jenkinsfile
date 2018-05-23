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
                    bat(/cd C:\Progra~2\Jenkins\workspace\PracticaTesting>C:\Progra~2\Jenkins\tools\hudson.tasks.Maven_MavenInstallation\M3\bin\mvn -f tic-tac-toe-enunciado\pom.xml test/)
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