/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.handlers

import org.gradle.api.Project

class PrepareHandlerFactory {

    public static IPrepareHandler createHandler(Project project) {

        if(project.appscanSettings.sourceCodeOnly)
            return new SourceCodeProjectHandler(project)
        else if(project.plugins.hasPlugin("com.android.application"))
            return new AndroidProjectHandler(project);
        else if(project.plugins.hasPlugin("java") || project.plugins.hasPlugin("org.gradle.java")) //$NON-NLS-1$ //$NON-NLS-2$
            return new JavaProjectHandler(project);
        else
            return new DefaultProjectHandler(project);
    }
}
