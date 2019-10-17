/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.scan.IScanManager
import com.hcl.appscan.sdk.scan.ITarget
import org.gradle.BuildAdapter
import org.gradle.api.Project

abstract class SecurityAction extends BuildAdapter {

    private Project m_project;
    private Collection<ITarget> m_targets;

    public SecurityAction(Project project, Collection<ITarget> targets) {
        m_project = project;
        m_targets = targets;
    }

    protected Project getProject() {
        return m_project;
    }

    protected Collection<ITarget> getTargets() {
        return m_targets;
    }

    protected abstract Map<String, String> getOptions();

    protected abstract IScanManager initScanManager();
}
