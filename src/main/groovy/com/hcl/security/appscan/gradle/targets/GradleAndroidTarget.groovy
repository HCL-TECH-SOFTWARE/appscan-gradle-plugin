package com.hcl.security.appscan.gradle.targets

import com.hcl.appscan.sdk.scanners.sast.targets.DefaultTarget
import org.gradle.api.Project

class GradleAndroidTarget extends DefaultTarget{

    private Project m_project;

    public GradleAndroidTarget(Project project) {
        m_project = project
    }

    @Override
    File getTargetFile() {
        return m_project.android.sourceSets.main.manifest.srcFile
    }

    @Override
    Map<String, String> getProperties() {
        return new HashMap<String, String>()
    }
}
