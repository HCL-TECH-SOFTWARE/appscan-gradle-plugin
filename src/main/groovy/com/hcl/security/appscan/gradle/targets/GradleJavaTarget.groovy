/**
 * @ Copyright HCL Technologies Ltd. 2018, 2020.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.targets

import com.hcl.appscan.sdk.scanners.sast.targets.JavaTarget
import com.hcl.security.appscan.gradle.ASoCConstants
import com.hcl.security.appscan.gradle.utils.JavaUtil
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

class GradleJavaTarget extends JavaTarget implements ASoCConstants{

    private Project m_project

    GradleJavaTarget(Project project) {
        m_project = project
    }

    @Override
    String getClasspath() {
        String classpath = ""; //$NON-NLS-1$

        for(SourceSet sourceSet : m_project.sourceSets) {
            classpath += sourceSet.compileClasspath.asPath + File.pathSeparator;
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

    @Override
    Map<String, String> getProperties() {
        Map<String, String> buildInfos = super.getProperties();
        buildInfos.put("package_includes", JavaUtil.getNamespacesAsString(m_project));
        return buildInfos;
    }
}
