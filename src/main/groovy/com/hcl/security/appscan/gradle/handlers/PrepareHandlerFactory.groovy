/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.handlers

import com.hcl.security.appscan.gradle.ASoCConstants
import org.gradle.api.Project

class PrepareHandlerFactory {

    public static IPrepareHandler createHandler(Project project) {

        if(System.getProperty(ASoCConstants.PROP_SOURCE_CODE_ONLY) != null)
            return new JavaSourceCodeOnlyHandler(project)
        else if (project.plugins.hasPlugin("com.android.application"))
            return new AndroidProjectHandler(project);
        else if(project.plugins.hasPlugin("java") || project.plugins.hasPlugin("org.gradle.java")) //$NON-NLS-1$ //$NON-NLS-2$
            return new JavaProjectHandler(project);
        else
            return new DefaultProjectHandler(project);
    }
}
