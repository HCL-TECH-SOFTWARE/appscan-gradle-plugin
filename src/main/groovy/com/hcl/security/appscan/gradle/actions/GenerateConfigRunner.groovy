/**
* @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.error.AppScanException
import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.appscan.sdk.scanners.sast.SASTScanManager
import com.hcl.security.appscan.gradle.tasks.SecurityTask
import org.gradle.BuildResult
import org.gradle.api.GradleScriptException
import org.gradle.api.Project

class GenerateConfigRunner extends SASTSecurityAction {

    public GenerateConfigRunner(Project project, Collection<ITarget> targets) {
        super(project, targets)
    }

    @Override
    void buildFinished(BuildResult result) {
        try {
            SASTScanManager manager = initScanManager()
            manager.createConfig()
            SecurityTask.cleanUp()
        } catch(AppScanException e) {
            throw new GradleScriptException("Failed generating appscan-config.xml.", e)
        }
    }
}
