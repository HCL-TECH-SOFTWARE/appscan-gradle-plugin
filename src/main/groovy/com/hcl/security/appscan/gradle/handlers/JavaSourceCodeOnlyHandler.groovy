/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.handlers

import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.appscan.sdk.scanners.sast.targets.GenericTarget
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.SourceSet

class JavaSourceCodeOnlyHandler implements IPrepareHandler {

    private Project m_project;
    private List<ITarget> m_targets;

    public JavaSourceCodeOnlyHandler(Project project) {
        m_project = project;
        m_targets = new ArrayList<ITarget>();
    }

    @Override
    List<ITarget> getTargets() {
        for (SourceSet sourceset : m_project.sourceSets) {
            if (!'test'.equalsIgnoreCase(sourceset.name)) { // Don't add "test" source set
                for (File sourceDir : sourceset.allSource.srcDirs) {
                    m_targets.add(new GenericTarget(sourceDir.toString()));
                }
            }
        }

        // Add web content for JavaEE application
        if (m_project.plugins.withType(WarPlugin).size() > 0) {
            m_targets.add(new GenericTarget(m_project.webAppDir.toString()));
        }
        return m_targets
    }
}
