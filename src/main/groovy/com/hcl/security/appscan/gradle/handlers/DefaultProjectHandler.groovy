/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.handlers

import com.hcl.appscan.sdk.scan.ITarget
import org.gradle.api.Project

class DefaultProjectHandler implements IPrepareHandler {

    private Project m_project;
    private List<ITarget> m_targets;

    public DefaultProjectHandler(Project project) {
        m_project = project;
        m_targets = new ArrayList<ITarget>();
    }

    @Override
    List<ITarget> getTargets() {
        //m_targets.add(new GradleDefaultTarget(m_project))
        return m_targets
    }
}
