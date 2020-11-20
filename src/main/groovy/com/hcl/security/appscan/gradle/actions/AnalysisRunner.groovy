/**
* @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.error.AppScanException
import com.hcl.appscan.sdk.logging.DefaultProgress
import com.hcl.appscan.sdk.scan.IScanServiceProvider
import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.appscan.sdk.scanners.sast.SASTScanManager
import com.hcl.security.appscan.gradle.tasks.SecurityTask
import org.gradle.BuildResult
import org.gradle.api.GradleScriptException
import org.gradle.api.Project

class AnalysisRunner extends SASTSecurityAction {

    private IScanServiceProvider m_provider;

    public AnalysisRunner(Project project, Collection<ITarget> targets, IScanServiceProvider provider) {
        super(project, targets);
        m_provider = provider;
    }

    @Override
    void buildFinished(BuildResult result) {
        try {
            SASTScanManager manager = getScanManager();
            manager.analyze(new DefaultProgress(), getOptions(), m_provider);
            getProject().logger.info("AppScan ID: " + manager.getScanId());
            SecurityTask.clearTargets();
        } catch(AppScanException e) {
            throw new GradleScriptException("Failed to submit the security scan for analysis.", e)
        }
    }
}
