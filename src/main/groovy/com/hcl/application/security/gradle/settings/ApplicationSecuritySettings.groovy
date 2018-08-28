/**
 * @ Copyright HCL Technologies Ltd. 2018.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.application.security.gradle.settings

import com.hcl.application.security.gradle.ASoCConstants
import org.gradle.api.Project

class ApplicationSecuritySettings implements ASoCConstants {

    String irxDir;
    String irxName;
    String appscanKey;
    String appscanSecret;
    String appId;

    public ApplicationSecuritySettings(Project project) {
        irxDir = System.getProperty(PROP_IRX_DIR) ?: project.rootProject.buildDir.getAbsolutePath();
        irxName = System.getProperty(PROP_IRX_NAME) ?: project.rootProject.getName();
        appscanKey = System.getProperty(PROP_KEY) ?: project.rootProject.properties.appscanKey;
        appscanSecret = System.getProperty(PROP_SECRET) ?: project.rootProject.properties.appscanSecret;
        appId = System.getProperty(PROP_APP_ID) ?: ""; //$NON-NLS-1$
    }
}
