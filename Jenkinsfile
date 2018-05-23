#!groovy

node {
  stage 'Compilar'

  echo 'Configurando variables'
  def mvnHome = tool 'M3'
  env.PATH = "${mvnHome}/bin:${env.PATH}"
  echo "var mvnHome='${mvnHome}'"
  echo "var env.PATH='${env.PATH}'"

  echo 'Descargando código de SCM'
  sh 'rm -rf *'
  checkout scm

  echo 'Compilando aplicación'
  sh 'mvn clean compile'

  stage 'Test'
  echo 'Ejecutando tests'
  try{
    sh 'mvn verify'
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
  } catch (err) {
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    if (currentBuild.result == 'UNSTABLE')
      currentBuild.result = 'FAILURE'
      throw err
  }

  stage 'Instalar'
  echo 'Instala el paquete generado en el repositorio Maven'
  sh 'mvn install -Dmaven.test.skip=true'

  stage 'Archivar'
  echo 'Archiva el paquete generado en Jenkins'
  step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar, **/target/*.war', fingerprint:true])
}