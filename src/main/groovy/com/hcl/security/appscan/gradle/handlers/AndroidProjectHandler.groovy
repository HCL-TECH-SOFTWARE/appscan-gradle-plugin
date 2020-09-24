package com.hcl.security.appscan.gradle.handlers

import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.security.appscan.gradle.targets.GradleAndroidTarget
import org.gradle.api.Project


class AndroidProjectHandler implements IPrepareHandler {

    private Project m_project;
    private List<ITarget> m_targets;

    public AndroidProjectHandler(Project project) {
        m_project = project;
        m_targets = new ArrayList<ITarget>();
    }

    @Override
    List<ITarget> getTargets() {
        m_targets.add(new GradleAndroidTarget(m_project))
        return m_targets
    }
}
