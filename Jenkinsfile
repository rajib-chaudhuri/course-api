@Library('dxp-pipeline-library')_
node {
	def mvnHome	
	def methods = new libraryFunctions() 
	// This can be nexus3 or nexus2
	def NEXUS_VERSION = "nexus3"
	// This can be http or https
	def NEXUS_PROTOCOL = "http"
	// Where your Nexus is running
	def NEXUS_URL = "ec2-3-9-21-107.eu-west-2.compute.amazonaws.com:8081"
	// Repository where we will upload the artifact
	def NEXUS_REPOSITORY = "DXP-SNAPSHOT"
	// Jenkins credential id to authenticate to Nexus OSS
	def NEXUS_CREDENTIAL_ID = "nexus-credentials-dxp"
	
	stage ('clean') {
		
		sh 'bash stopContainer.sh' 		
		sh 'docker system prune -a --volumes -f'
		sh 'docker container prune -f'
		sh 'docker image prune -a -f'
		echo "~~~docker cleaning done ~~~~~"
	}
	stage('Preparation') { // for display purposes
		// Get some code from a GitHub repository
		def repo = "https://github.com/suswan-mondal/course-api.git"		
		checkoutFromRepo(repo)

	}
	stage('Build') {

		// Get the Maven tool.
		mvnHome = tool 'mvn3.6'

		// Run the maven builds		
		mavenBuild(mvnHome)
		sh 'cp /var/lib/jenkins/workspace/dxpcommerce/target/course-api-0.0.1-SNAPSHOT.jar /var/lib/jenkins/workspace/dxpcommerce' 
		
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
		// junit '**/target/surefire-reports/TEST-*.xml'
		archive 'target/*.jar'
	}
}