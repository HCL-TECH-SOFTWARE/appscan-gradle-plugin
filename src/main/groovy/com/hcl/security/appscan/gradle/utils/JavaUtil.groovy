/**
 * @ Copyright HCL Technologies Ltd. 2020.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.utils

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

class JavaUtil {

    static Set<String> getNamespaces(Project project) {
        Set<String> namespaces = new HashSet<String>()

        for(SourceSet sourceSet : project.sourceSets) {
            for(File outDir : sourceSet.getOutput().getClassesDirs()) {
                for(File subDir : outDir.listFiles()) {
                    if(subDir.isDirectory())
                        namespaces.addAll(getNamespacesForDirectory(subDir))
                }
            }
        }

        return namespaces
    }

    static String getNamespacesAsString(Project project) {
        String separator = ";"
        String namespaces = ""

        for(String namespace : getNamespaces(project)) {
            namespaces += namespace + separator
        }

        return namespaces.endsWith(separator) ? namespaces.substring(0, namespaces.length() - 1) : namespaces
    }

    private static Set<String> getNamespacesForDirectory(File sourceDir) {
        Set<String> namespaces = new HashSet<String>()

        if(sourceDir.isDirectory()) {
            for(File child : sourceDir.listFiles()) {
                if(child.isDirectory())
                    namespaces.add(sourceDir.getName() + "." + child.getName())
            }
        }

        return namespaces
    }
}
