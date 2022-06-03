/**
 * @ Copyright HCL Technologies Ltd. 2018, 2022.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.settings

import com.hcl.security.appscan.gradle.ASoCConstants
import org.gradle.api.Project

class AppScanSettings implements ASoCConstants {

    String irxDir;
    String irxName;
    String appscanKey;
    String appscanSecret;
    String appId;
    String namespaces;
    boolean sourceCodeOnly;
    boolean openSourceOnly;
    boolean staticAnalysisOnly;

    public AppScanSettings(Project project) {
        irxDir = System.getProperty(PROP_IRX_DIR) ?: project.rootProject.buildDir.getAbsolutePath();
        irxName = System.getProperty(PROP_IRX_NAME) ?: project.rootProject.getName();
        appscanKey = System.getProperty(PROP_KEY) ?: project.rootProject.properties.appscanKey;
        appscanSecret = System.getProperty(PROP_SECRET) ?: project.rootProject.properties.appscanSecret;
        appId = System.getProperty(PROP_APP_ID) ?: ""; //$NON-NLS-1$
        sourceCodeOnly = System.getProperty("sourceCodeOnly") != null ?: project.rootProject.properties.sourceCodeOnly;
        openSourceOnly = System.getProperty("openSourceOnly") != null ?: project.rootProject.properties.openSourceOnly;
        staticAnalysisOnly = System.getProperty("staticAnalysisOnly") != null ?: project.rootProject.properties.staticAnalysisOnly;
        namespaces = System.getProperty(PROP_NAMESPACES);
    }
}
