/**
* @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.error.AppScanException
import com.hcl.appscan.sdk.logging.DefaultProgress
import com.hcl.appscan.sdk.scan.IScanManager
import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.security.appscan.gradle.tasks.SecurityTask
import org.gradle.BuildResult
import org.gradle.api.GradleScriptException
import org.gradle.api.Project

class PrepareRunner extends SASTSecurityAction {

    public PrepareRunner(Project project, Collection<ITarget> targets) {
        super(project, targets);
    }

    @Override
    void buildFinished(BuildResult result) {
        try {
            IScanManager manager = initScanManager();
            manager.prepare(new DefaultProgress(), getOptions());
            SecurityTask.actionsWhenBuildFinished();
        } catch(AppScanException e) {
            throw new GradleScriptException("Failed preparing for the security scan.", e)
        }
    }
}
