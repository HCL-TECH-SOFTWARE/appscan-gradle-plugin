# IBM Application Security on Cloud Gradle Plugin

The IBM Application Security on Cloud Gradle plugin is used to automate the scanning of Java and Java web projects in Gradle.  It generates an IRX file for Gradle projects that have the "java" plugin and/or "war" plugins applied.  It can optionally submit the generated IRX file to the cloud service for analysis.

# Usage:

To use the plugin, add the following lines to build.gradle:

For Gradle 2.1 and later:

	plugins {
		id "com.ibm.application.security" version "1.0.0"
	}

For older Gradle versions:

	buildscript {
		repositories {
	    		maven { url "https://plugins.gradle.org/m2/" }
	  	}
	  	dependencies { classpath "gradle.plugin.com.ibm.security:application-security-gradle-plugin:1.0.0" }
	}

	apply plugin: 'com.ibm.application.security'

# Prerequisites:

- An account on the [IBM Application Security on Cloud](https://www.ibm.com/marketplace/cloud/application-security-on-cloud/) service. You'll need to [create an application](http://www.ibm.com/support/knowledgecenter/SSYJJF_1.0.0/ApplicationSecurityonCloud/ent_create_application.html) on the service to associate your scans with.

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

You can generate an API key id/secret [here](https://appscan.ibmcloud.com/AsoCUI/serviceui/main/admin/apiKey).

# License

All files found in this project are licensed under the [Apache License 2.0](LICENSE).

