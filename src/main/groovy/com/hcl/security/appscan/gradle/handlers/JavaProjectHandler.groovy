/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.handlers

import com.hcl.security.appscan.gradle.targets.GradleJEETarget
import com.hcl.security.appscan.gradle.targets.GradleJavaTarget
import com.hcl.appscan.sdk.scan.ITarget
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.SourceSet

class JavaProjectHandler implements IPrepareHandler {

    private Project m_project;
    private List<ITarget> m_targets;

    public JavaProjectHandler(Project project) {
        m_project = project;
        m_targets = new ArrayList<ITarget>();
    }

    @Override
    List<ITarget> getTargets() {
        if(m_project.plugins.withType(WarPlugin).size() > 0)
            m_targets.add(new GradleJEETarget(m_project));
        else if(hasSourceFiles())
            m_targets.add(new GradleJavaTarget(m_project));

        return m_targets
    }

    private boolean hasSourceFiles() {
        for(SourceSet sourceset : m_project.sourceSets) {
            for (File sourceDir : sourceset.java.srcDirs) {
                if (sourceDir.isDirectory() && sourceDir.listFiles().length > 0)
                    return true;
            }
        }
        return false;
    }
}
