@Library('dxp-pipeline-library')_
node {
	def mvnHome	
	def methods = new libraryFunctions() 
	// This can be nexus3 or nexus2
	def NEXUS_VERSION = "nexus3"
	// This can be http or https
	def NEXUS_PROTOCOL = "http"
	
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
	stage('publish to nexus'){
		echo "~~~~~ publish to nexus~~~~"	
		// Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
		pom = readMavenPom file: "pom.xml";
		// Find built artifact under target folder
		filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
		// Print some info from the artifact found
		echo "suswan---------------->>>${pom.packaging}"
		echo "~~name-->>> ${filesByGlob[0].name}~~path-->>> ${filesByGlob[0].path}~~directory-->>> ${filesByGlob[0].directory}~~length-->>> ${filesByGlob[0].length}~~lastModified-->>> ${filesByGlob[0].lastModified}"
		
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