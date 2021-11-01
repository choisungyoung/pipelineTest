node {
    // env.JAVA_HOME = "${jdk}"
    env.MAVEN_HOME = "/var/jenkins_home/workspace/bin/maven/3.8.1/bin"
    
    //timestamp 기능
    timestamps {
        // 빌드 결과에 따른 동작 정의
        try {
            stage('Git Pull') {
                git (
                    branch: "main",
                    credentialsId: "choisungyoung",
                    url: "https://github.com/choisungyoung/pipelineTest.git"
                )
            }
            stage('Build') {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
            stage('Test') {
                if (!params.TEST_SKIP) {
                    echo "start test"
                }
            }
            echo "Build Success"
        } catch (e) {
            echo "Build Failure"
            throw e

        } finally  {
            echo "Build Finish"
        }
    }
    properties([
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '7', numToKeepStr: '5')), // 오래된 빌드 삭제
        parameters([booleanParam(name: 'TEST_SKIP', defaultValue: true, description: '테스트 스킵 여부')]) // 파라미터 정의
    ])
}