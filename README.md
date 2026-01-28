# HCL AppScan Gradle Plugin

Harness the power of Static Application Security Testing (SAST) with HCL AppScan on Cloud, a SaaS solution, to eliminate application vulnerabilities before deployment.
You can also use HCL AppScan 360°, a cloud-native, self-managed platform, for vulnerability elimination. Both solutions integrate directly into the SDLC and provide
static, dynamic, and open-source testing.

You can submit static and open-source scans directly from the HCL AppScan Gradle plug-in, or use it to generate an IRX file to submit to the service later. Results are
ready quickly (90% are ready in less than one hour) and are honed by Intelligent Finding Analytics (IFA). IFA uses HCL's AI capabilities to reduce false positives and
other noise by an average of more than 98%. IFA also displays optimal locations for developers to fix multiple vulnerabilities in the code. [Learn more about Intelligent
Finding Analytics](https://www.hcl-software.com/blog/appscan/intelligent-finding-analytics-your-cognitive-computing-application-security-expert).

Not yet an AppScan on Cloud or AppScan 360° customer? [Get a free trial of AppScan on Cloud](https://cloud.appscan.com/), or [get a free trial of AppScan 360°](https://www.hcl-software.com/appscan/products/appscan360/contact) to use with this plug-in. 

# Prerequisites:

- An account on the [HCL AppScan on Cloud](https://cloud.appscan.com/) service. You'll need to [create an application](https://help.hcltechsw.com/appscan/ASoC/ent_create_application.html) on the service to associate your scans with.
- Access to an instance of HCL AppScan 360° to execute scans in that environment. [Learn more about AppScan 360° features and installation](https://help.hcl-software.com/appscan/360/2.0.0/home.html).
 
# Usage:

You can find usage information and the latest version on the [plug-in page](https://plugins.gradle.org/plugin/com.hcl.security.appscan) in the Gradle plugins repository.

To use the plug-in, add the following lines to _build.gradle_, and replace \<version\> with the version of the plug-in you want to use.

For Gradle 2.1 and later:

	plugins {
		id "com.hcl.security.appscan" version "<version>"
	}

For older Gradle versions:

	buildscript {
		repositories {
	    		maven { url "https://plugins.gradle.org/m2/" }
	  	}
	  dependencies { classpath "gradle.plugin.com.hcl.security:application-security-gradle-plugin:<version>" }
	}

	apply plugin: 'com.hcl.security.appscan'

# Tasks:

- appscan-prepare:
	Generates an IRX file for all Java and WAR projects in the build. By default, the task generates the IRX file in the root project's "build" directory.

- appscan-analyze:
  Generates an IRX file and submits it to the AppScan on Cloud service or AppScan 360° for analysis. This task requires an API key, secret, and application ID. serviceUrl is an additional parameter needed for AppScan 360°.
  
# Configurable Options:

	OPTION:				     DEFAULT VALUE				                          DESCRIPTION
	irxName           	The name of the root project                  The name of the generated .irx file.
	irxDir            	The build directory of the root project.      The location for the generated .irx file.
	appId             	null - Required for 'appscan-analyze'         The id of the application in the cloud service.
	appscanKey        	null - Required for 'appscan-analyze'         The user's API key id for authentication.
	appscanSecret     	null - Required for 'appscan-analyze'         The user's API key secret for authentication.
	namespaces	  	    null					      				  Override automatic namespace detection. Set to "" to disable namespace detection.
    sourceCodeOnly    	false					      				  If set to true, only scan source code.
    openSourceOnly	  	false					      				  Only run software composition analysis (SCA). Do not run static analysis.
	staticAnalysisOnly	false					      				  Only run static analysis. Do not run software composition analysis (SCA).
 	jspCompiler     	Default Tomcat JSP Compiler                   The JSP compiler path.
	thirdParty		    false					      				  Include known third party packages in static analysis (not recommended).
	serviceUrl		    null					      				  Required for AppScan 360°.
	acceptssl		    false					      				  Ignore untrusted certificates when connecting to AppScan 360°, and not applicable to AppScan on Cloud.

You can set all options through JVM parameters on the command line using the syntax -Doption=value. For example:

	gradle appscan-prepare -DirxName=MyApp

You can also set all options using an appscanSettings block in the build script. For example:

	appscanSettings {
		irxName="MyApp"
		irxDir="/myApplication/sample"
	}

You can specify the appscanKey and appscanSecret options in the user's gradle.properties file. This avoids the need to specify authentication information in the build script or command line. For example, add the following lines to ~/.gradle/gradle.properties (create the file if it doesn't exist):

	appscanKey="2358cd02-3fs3-322c-62c9-b5cc63c61f2a"
	appscanSecret="qU939siTXgF7csk3jSig+Vza7ilWLu/Uy/ReWye5E/c="

[Generate an API key ID/secret for AppScan On Cloud](https://cloud.appscan.com/main/apikey). [Learn how to generate them for AppScan 360°](https://help.hcl-software.com/appscan/360/2.0.0/appseccloud_generate_api_key_cm.html).

To scan only source code, use the -DsourceCodeOnly option on the command line. For example:

	gradle appscan-prepare -DsourceCodeOnly


# License

All files found in this project are licensed under the [Apache License 2.0](LICENSE).

