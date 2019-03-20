node{
    stage('Checkout')
    {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4b53a458-6c25-4765-acb2-6e33d865ad4c', url: 'https://github.com/suswan-mondal/course-api.git']]])
    }
    stage('Static code analysis')
    {
        echo "Static code analysis"
    }
    stage('build')
    {
        echo "build"
    }
    stage('Unit Testing')
    {
        echo "unit testing"
    }
}
