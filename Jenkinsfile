@Library('dxp-pipeline-library')_
node {
	def mvnHome	
	def methods = new libraryFunctions() 
	// Get the Maven tool.
	mvnHome = tool 'mvn3.6'
	def applicationName='CourseApi'
	stage ('clean') {
		sh "echo ${applicationName}"
		sh "bash stopContainer.sh ${applicationName}"
		sh 'docker system prune -a --volumes -f'
		sh 'docker container prune -f'
		sh 'docker image prune -a -f'
		echo "~~~docker cleaning done ~~~~~"
	}
	stage('Code checkout') { // for display purposes
		// Get some code from a GitHub repository
		def repo = "https://github.com/suswan-mondal/course-api.git"		
		checkoutFromRepo(repo)

	}
	stage('Code quality analysis') {
		echo "~~~SonarQube analysis starts ~~~~~"
		withSonarQubeEnv('dxp sonarqube server') {
			//sh 'mvn clean package sonar:sonar'
			sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package sonar:sonar"
			sh 'cp /var/lib/jenkins/workspace/dxpcommerce/target/course-api-0.0.1-SNAPSHOT.jar /var/lib/jenkins/workspace/dxpcommerce' 
		} // SonarQube taskId is automatically attached to the pipeline context
	}	
	stage("Quality Gate"){
		echo "~~~Quality Gate starts ~~~~"
		timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
			def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
			
			echo "----status---->>>> ${qg.status}"			
			if (qg.status != 'OK') {
				error "Pipeline aborted due to quality gate failure: ${qg.status}"
			}
		}
	}
	//stage('Build') {
		// Run the maven builds		
		//mavenBuild(mvnHome)
		//sh 'cp /var/lib/jenkins/workspace/dxpcommerce/target/course-api-0.0.1-SNAPSHOT.jar /var/lib/jenkins/workspace/dxpcommerce' 
		
	//}
	stage('publish to nexus'){
		//publishNexus(NEXUS_VERSION,NEXUS_PROTOCOL,NEXUS_URL,NEXUS_REPOSITORY,NEXUS_CREDENTIAL_ID)
		publishNexus()
	}
	stage ('DockerBuild Image'){
		echo "~~~~~ DockerBuild Images~~~~"	
		app = docker.build("suswan/course")		 
		docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
        //app.push("${env.BUILD_NUMBER}")
		app.push("1.0")
        // app.push("latest")
        } 
		
		echo "~~~~~ push to docker hub done~~~~"
	}
	stage('DockerBuild run'){
		echo "~~~~~ DockerBuild deploy~~~~"
		sh 'chmod +x runContainer.sh'
		sh 'nohup ./runContainer.sh > /dev/null 2>&1 &'	
		
		//sh 'docker run --name  CourseApiContainer -p 80:8090 suswan/course'
	}
	
	stage('Deploy'){
		echo "~~~~~ deploy~~~~"
		//bat "set"		
		def jarFileName='course-api-0.0.1-SNAPSHOT.jar'
		//deploy(jarFileName)
				
	}
	stage('Results') {
		echo "~~~~~ Results~~~~"
		// junit '**/target/surefire-reports/TEST-*.xml'
		//archive 'target/*.jar'
		hygieiaBuildPublishStep buildStatus: 'Success'
	}
}