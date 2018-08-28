/**
 * @ Copyright HCL Technologies Ltd. 2018.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.application.security.gradle.targets

import com.hcl.application.security.gradle.ASoCConstants
import com.hcl.appscan.sdk.scanners.sast.targets.JavaTarget
import org.gradle.api.tasks.SourceSet
import org.gradle.api.Project

class GradleJavaTarget extends JavaTarget implements ASoCConstants{

    private Project m_project

    GradleJavaTarget(Project project) {
        m_project = project
    }

    @Override
    String getClasspath() {
        String classpath = ""; //$NON-NLS-1$

        for(SourceSet sourceSet : m_project.sourceSets) {
            classpath += sourceSet.compileClasspath.asPath + ";"; //$NON-NLS-1$
        }

        return classpath;
    }

    @Override
    String getJava() {
        String jdk = System.getenv(VAR_JAVA_HOME);
        return jdk == null ? System.getProperty(PROP_JAVA_HOME) : jdk;
    }

    @Override
    File getTargetFile() {
        return m_project.hasProperty("jar") ? m_project.jar.archivePath : m_project.sourceSets.main.java.outputDir; //$NON-NLS-1$
    }
}
