# HCL AppScan on Cloud Gradle Plugin

Apply the power of static application security testing with HCL AppScan on Cloud – a SaaS solution that helps to eliminate vulnerabilities from applications before they are deployed. HCL AppScan on Cloud integrates directly into the SDLC, providing static, dynamic, mobile and open source testing.

You can submit static and open source scans directly from the HCL AppScan on Cloud Gradle plugin or use it to generate an IRX file for later submission to the service. The results are ready quickly (90% are ready in less than one hour) having been honed by Intelligent Finding Analytics, which uses HCL's Artificial Intelligence capabilities to greatly reduce false positives and other noise by an average of more than 98%. IFA also displays optimal locations for developers to fix multiple vulnerabilities in the code. Click [here](https://securityintelligence.com/intelligent-finding-analytics-cognitive-computing-application-security-expert/) for more information.

Not yet a customer of HCL AppScan on Cloud? Click [here](https://cloud.appscan.com) for a free trial of Application Security on Cloud to use with this plugin

# Prerequisites:

- An account on the [HCL AppScan on Cloud](https://cloud.appscan.com/) service. You'll need to [create an application](https://help.hcltechsw.com/appscan/ASoC/ent_create_application.html) on the service to associate your scans with.

# Usage:

To use the plugin, add the following lines to build.gradle:

For Gradle 2.1 and later:

	plugins {
		id "com.hcl.application.security" version "1.0.0"
	}

For older Gradle versions:

	buildscript {
		repositories {
	    		maven { url "https://plugins.gradle.org/m2/" }
	  	}
	  dependencies { classpath "gradle.plugin.com.hcl.security:application-security-gradle-plugin:1.0.0" }
	}

	apply plugin: 'com.hcl.application.security'

# Tasks:

- appscan-prepare:
	Generates an IRX file for all Java and War projects in the build. The IRX file will be generated in the root project's "build" directory by default.

- appscan-analyze:
  Generates an IRX file and submits it to the cloud service for analysis. This task requires an api key, secret, and application id.
  
# Configurable Options:

	OPTION:			DEFAULT VALUE				DESCRIPTION
	irxName           The name of the root project                  The name of the generated .irx file.
	irxDir            The build directory of the root project.      The location for the generated .irx file.
	appId             null - Required for 'appscan-analyze'         The id of the application in the cloud service.
	appscanKey        null - Required for 'appscan-analyze'         The user's API key id for authentication.
	appscanSecret     null - Required for 'appscan-analyze'         The user's API key secret for authentication.

All options can be set through JVM parameters on the command line using the syntax -Doption=value. For example:

	gradle appscan-prepare -DirxName=MyApp

All options can also be set using an "applicationsecurity" block in the build script. For example:

	applicationsecurity {
		irxName="MyApp"
		irxDir="/myApplication/sample"
	}

The appscanKey and appscanSecret options can be specified in the user's gradle.properties file. This avoids the need to specify authentication information in the build script or command line. For example, add the following lines to ~/.gradle/gradle.properties (create the file if it doesn't exist):

	appscanKey="2358cd02-3fs3-322c-62c9-b5cc63c61f2a"
	appscanSecret="qU939siTXgF7csk3jSig+Vza7ilWLu/Uy/ReWye5E/c="

You can generate an API key id/secret [here](https://cloud.appscan.com/api/ideclientuilogin).

# License

All files found in this project are licensed under the [Apache License 2.0](LICENSE).

