/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.targets

import com.hcl.security.appscan.gradle.ASoCConstants
import com.hcl.appscan.sdk.scanners.sast.targets.JEETarget
import org.gradle.api.Project

class GradleJEETarget extends JEETarget implements ASoCConstants {

    private static String DEFAULT_JSP_COMPILER = "Default Tomcat JSP Compiler"; //$NON-NLS-1$

    private Project m_project

    public GradleJEETarget(Project project) {
        m_project = project
    }

    @Override
    String getJSPCompiler() {
        String jspCompiler = System.getProperty(JSP_COMPILER);
        if(jspCompiler != null && jspCompiler != "true" && !(jspCompiler.trim().isEmpty())) //$NON-NLS-1$
            return jspCompiler;

        return DEFAULT_JSP_COMPILER;
    }

    @Override
    String getClasspath() {
        return m_project.sourceSets.main.java.outputDir
    }

    @Override
    String getJava() {
        String jdk = System.getenv(VAR_JAVA_HOME);
        return jdk == null ? System.getProperty(PROP_JAVA_HOME) : jdk;
    }

    @Override
    File getTargetFile() {
        File target = m_project.war.archivePath
        return target.isFile() ? target : m_project.webAppDir
    }
}
