/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.tasks

import com.hcl.security.appscan.gradle.actions.AnalysisRunner
import com.hcl.security.appscan.gradle.auth.GradleAuthenticationProvider
import com.hcl.security.appscan.gradle.error.ASoCException
import com.hcl.appscan.sdk.auth.IAuthenticationProvider
import com.hcl.appscan.sdk.logging.DefaultProgress
import com.hcl.appscan.sdk.scan.CloudScanServiceProvider
import com.hcl.appscan.sdk.scan.IScanServiceProvider
import org.gradle.BuildAdapter
import org.gradle.api.tasks.TaskExecutionException

class AnalyzeTask extends SecurityTask {

    @Override
    protected BuildAdapter getPostBuildAction() {
        String key = project.appscanSettings.appscanKey;
        String secret = project.appscanSettings.appscanSecret;

        if(key == null || secret == null) {
            ASoCException e = new ASoCException("No credentials provided. Use -DappscanKey=<key> -DappscanSecret=<secret> to specify credentials.")
            throw new TaskExecutionException(this, e);
        }
        else if(project.appscanSettings.appId == "") { //$NON-NLS-1$
            ASoCException e = new ASoCException("No application Id provided. Use -DappId=<app ID> to specify the application ID.")
            throw new TaskExecutionException(this, e);
        }

        IAuthenticationProvider authProvider = new GradleAuthenticationProvider(key, secret);
        IScanServiceProvider serviceProvider = new CloudScanServiceProvider(new DefaultProgress(), authProvider);
        return new AnalysisRunner(project, getTargets(), serviceProvider);
    }
}
