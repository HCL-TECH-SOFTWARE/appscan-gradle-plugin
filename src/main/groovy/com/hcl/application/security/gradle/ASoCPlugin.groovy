/**
 * @ Copyright HCL Technologies Ltd. 2018.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.application.security.gradle

import com.hcl.application.security.gradle.settings.ApplicationSecuritySettings
import com.hcl.application.security.gradle.tasks.AnalyzeTask
import com.hcl.application.security.gradle.tasks.PrepareTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.WarPlugin

class ASoCPlugin implements Plugin<Project> {

    void apply(Project project) {

		project.subprojects*.apply(plugin: 'com.hcl.application.security')
		project.extensions.create("applicationsecurity", ApplicationSecuritySettings, project)

		project.task('appscan-prepare',
					description: "Generates an IRX file for all projects in the build.",
					type: PrepareTask) {
					inputfiles = { getInputFiles(project) }
					outputs.upToDateWhen {false}
		}

		project.task('appscan-analyze',
					description: "Generates an IRX file for all projects in the build and submits it to the cloud for analysis.",
					type: AnalyzeTask) {
					inputfiles = { getInputFiles(project) }
					outputs.upToDateWhen {false}
		}
	}

	private FileCollection getInputFiles(Project project) {
		FileCollection files;

		if (project.plugins.withType(WarPlugin).size() > 0)
			files = project.war.outputs.files;
		else if (project.plugins.withType(JavaPlugin).size() > 0)
			files = project.jar.outputs.files;
		else
			files = project.files(project.buildDir);

		return files;
	}
}


