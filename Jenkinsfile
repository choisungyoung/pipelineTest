node {
    def mvnHome
    
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
                mvnHome = tool 'maven-3.8.1'
            }
            stage('Maven Build') {
	            withEnv(["MVN_HOME=$mvnHome"]) {
		            if (isUnix()) {
		                //sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
		            } else {
		                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
		            }
		        }
            }
            stage('Image Build') {
               	app = docker.build("pipelinetest")	//docker 플러그인 설치해야 해당문법 가능. 결국 실행된는건 "docker build -t pipelinetest ."
               	
            	// Buildpacks이용한 이미지빌드방법
            	// sh './mvnw spring-boot:build-image'
            }
            stage('Image Push') {

            	docker.withRegistry('http://node2.example.com:30500', '89c2760a-7e6f-4137-a8a0-ac50fa72513d') { 
            		//app.push("${env.BUILD_NUMBER}") 
                    app.push("latest") 
        		}
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