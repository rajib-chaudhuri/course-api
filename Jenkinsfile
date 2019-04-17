@Library('dxp-pipeline-library')_
node {
	def mvnHome	
	//def methods = new libraryFunctions() 
	// Get the Maven tool.
	mvnHome = tool 'mvn3.6'
	def applicationName='courseapi'
	stage('Code checkout') {
		//echo "==========================================DXP Pipeline Library checkout starts====================================================="
		//def dxpLibraryRepo = "https://github.com/suswan-mondal/dxp-pipeline-library.git"
		//checkoutFromRepo(dxpLibraryRepo)	
		//echo "===========================================DXP Pipeline Library checkout ends====================================================="
	
		echo "==========================================Project Code checkout starts====================================================="
		// Get some code from a GitHub repository
		def projectRepo = "https://github.com/suswan-mondal/course-api.git"		
		checkoutFromRepo(projectRepo)		
		echo "==========================================Project Code checkout ends====================================================="
	}
	stage ('Docker image / container clean') {
		echo "==========================================Docker image / container clean starts====================================================="
		echo "applicationName---  ${applicationName}"
		//sh "bash stopContainer.sh ${applicationName}"
		sh "bash /var/lib/jenkins/workspace/dxpcommerce@libs/dxp-pipeline-library/vars/stopContainer.sh ${applicationName}"
		sh 'docker system prune -a --volumes -f'
		//sh 'docker container prune -f'
		//sh 'docker image prune -a -f'
		
		//sh 'docker container prune -f' //remove all stopped containers
		//sh 'docker image prune -a -f' // remove dangled (that is not tagged and is not used by any container) and unused images
		//sh 'docker volume prune -f' // remove all unused volumes
		echo "==========================================Docker image / container clean ends====================================================="
	}
	
	stage('Code quality analysis') {
		echo "==========================================Code quality analysis starts====================================================="
		withSonarQubeEnv('dxp sonarqube server') {
			//sh 'mvn clean package sonar:sonar'
			sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package sonar:sonar"
			sh 'cp /var/lib/jenkins/workspace/dxpcommerce/target/course-api-0.0.1-SNAPSHOT.jar /var/lib/jenkins/workspace/dxpcommerce' 
		} // SonarQube taskId is automatically attached to the pipeline context
		echo "==========================================Code quality analysis ends====================================================="
	}	
	stage("Quality Gate"){
		echo "==========================================Quality Gate starts====================================================="
		timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
			def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
			
			echo "----status---->>>> ${qg.status}"			
			if (qg.status != 'OK') {
				error "Pipeline aborted due to quality gate failure: ${qg.status}"
			}
		}
		echo "==========================================Quality Gate ends====================================================="
	}
	//stage('Build') {
		// Run the maven builds		
		//mavenBuild(mvnHome)
		//sh 'cp /var/lib/jenkins/workspace/dxpcommerce/target/course-api-0.0.1-SNAPSHOT.jar /var/lib/jenkins/workspace/dxpcommerce' 
		
	//}
	stage('publish to nexus'){
		echo "==========================================publish to nexus starts====================================================="
		//publishNexus(NEXUS_VERSION,NEXUS_PROTOCOL,NEXUS_URL,NEXUS_REPOSITORY,NEXUS_CREDENTIAL_ID)
		def NEXUS_REPOSITORY = 'DXP-SNAPSHOT'	
		publishNexus(NEXUS_REPOSITORY)
		echo "==========================================publish to nexus ends====================================================="
	}
	stage ('Docker Build/Push Image'){
		echo "==========================================Docker Build/Push Image starts====================================================="
		app = docker.build("suswan/${applicationName}")		
		
		docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
        //app.push("${env.BUILD_NUMBER}")
		app.push("1.0")
        } 
		
		echo "==========================================Docker Build/Push Image ends====================================================="
	}
	stage('DockerImage run'){
		echo "==========================================DockerImage run starts====================================================="
		//sh "chmod +x runContainer.sh"
		//sh "nohup ./runContainer.sh ${applicationName} > /dev/null 2>&1 &"
		sh "chmod +x /var/lib/jenkins/workspace/dxpcommerce@libs/dxp-pipeline-library/vars/runContainer.sh"
		sh "bash  /var/lib/jenkins/workspace/dxpcommerce@libs/dxp-pipeline-library/vars/runContainer.sh ${applicationName}"
		//sh "nohup ./runContainer.sh ${applicationName} > /dev/null 2>&1 && tail -f /dev/null"
		//sh 'chmod +x runContainer.sh'
		//sh 'nohup ./runContainer.sh > /dev/null 2>&1 &'
		
		//sh 'docker run --name  CourseApiContainer -p 80:8090 suswan/course'
		echo "==========================================DockerImage run starts====================================================="
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
		//hygieiaBuildPublishStep buildStatus: 'Success'
	}
}