/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.targets

import com.hcl.appscan.sdk.scanners.sast.targets.DefaultTarget
import org.gradle.api.Project

class GradleDefaultTarget extends DefaultTarget{

    private Project m_project;

    public GradleDefaultTarget(Project project) {
        m_project = project
    }

    @Override
    File getTargetFile() {
        return m_project.buildDir
    }

    @Override
    Map<String, String> getProperties() {
        return new HashMap<String, String>()
    }
}
