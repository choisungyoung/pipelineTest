node {
    // git
    def credentialsId = "choisungyoung"
    def appName = "pipelinetest"
    def gitRepositoryUrl = "https://github.com/choisungyoung/pipelineTest.git"

    // mavem
    def mvnHome
    def mvnVersion = 'maven-3.8.1'


    // docker
    def dockerRegistryUrl = "http://localhost:30500"
    def dockerRegistryCredentialId = "337541fe-5ce6-42b1-8da6-252037076eca"
    def dockerImageTag = "latest"

    // kubernetes
    def k8sCredentialsId = "kube-config"
    def k8sServerUrl = "https://10.100.0.104:6443"
    def k8sDeployFileName = "k8s-deploy.yaml"
    def k8sNameSpace = "jenkins"

    //timestamp 기능
    timestamps {
        // 빌드 결과에 따른 동작 정의
        try {
            stage('Git Pull') {
                git (
                    branch: "main",
                    credentialsId: credentialsId,
                    url: gitRepositoryUrl
                )
                mvnHome = tool mvnVersion
            }
            stage('Maven Build') {
	            withEnv(["MVN_HOME=$mvnHome"]) {
		            if (isUnix()) {
		                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
		            } else {
		                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
		            }
		        }
            }
            stage('Image Build') {
               	app = docker.build(appName)	//docker 플러그인 설치해야 해당문법 가능. 결국 실행된는건 "docker build -t pipelinetest ."
               	
            	// Buildpacks이용한 이미지빌드방법
            	// sh './mvnw spring-boot:build-image'
            }
            stage('Image Push') {

            	docker.withRegistry(dockerRegistryUrl, dockerRegistryCredentialId) { 
            		//app.push("${env.BUILD_NUMBER}") 
                    app.push(dockerImageTag) 
        		}
            }
            stage('Test') {
                if (!params.TEST_SKIP) {
                    echo "start test"
                }
            }
            stage('Deploy') {
                withKubeConfig([credentialsId: k8sCredentialsId, serverUrl: k8sServerUrl]) {
                    sh 'kubectl apply -f ' + k8sDeployFileName
                    // 이미지명, 테그가 바뀔경우 아래 명령 실행안해도됨.
                    sh 'kubectl rollout restart deployment -n ' + k8sNameSpace + " " + appName
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