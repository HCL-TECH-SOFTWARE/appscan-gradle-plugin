/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle

import com.hcl.security.appscan.gradle.settings.AppScanSettings
import com.hcl.security.appscan.gradle.tasks.AnalyzeTask
import com.hcl.security.appscan.gradle.tasks.PrepareTask
import com.hcl.security.appscan.gradle.tasks.GenerateConfigTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.WarPlugin

class ASoCPlugin implements Plugin<Project> {

    void apply(Project project) {

		project.subprojects*.apply(plugin: 'com.hcl.security.appscan')
		project.extensions.create("appscanSettings", AppScanSettings, project)

		project.task('appscan-prepare',
					description: "Generates an IRX file for all projects in the build.",
					type: PrepareTask) {
					inputfiles = { getInputFiles(project) }
					outputs.upToDateWhen {false}
		}

		project.task('appscan-analyze',
					description: "Generates an IRX file for all projects in the build and submits it to the service for analysis.",
					type: AnalyzeTask) {
					inputfiles = { getInputFiles(project) }
					outputs.upToDateWhen {false}
		}

		project.task('generate-appscan-config',
					description: "Generates appscan-config.xml file for all projects in the build.",
					type: GenerateConfigTask) {
					inputfiles = { getInputFiles(project) }
					outputs.upToDateWhen {false}
		}
	}

	private FileCollection getInputFiles(Project project) {
		FileCollection files;

		if (project.appscanSettings.sourceCodeOnly || project.plugins.hasPlugin('com.android.application'))
			files = project.files([]);
		else if (project.plugins.withType(WarPlugin).size() > 0)
			files = project.war.outputs.files;
		else if (project.plugins.withType(JavaPlugin).size() > 0)
			files = project.jar.outputs.files;
		else
			files = project.files(project.buildDir);

		return files;
	}
}


