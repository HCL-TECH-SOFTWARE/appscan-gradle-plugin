/**
 * @ Copyright HCL Technologies Ltd. 2020, 2022.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.utils


import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

class JavaUtil {

    private static final String s_separator = ";"

    static Set<String> getNamespaces(Project project) {
        Set<String> namespaces = new HashSet<String>()

        //Allow user to override automatic namespace detection.
        String namespaceOverride = project.appscanSettings.namespaces;
        if(namespaceOverride != null && !namespaceOverride.equalsIgnoreCase("true")) {
            String userNamespaces = namespaceOverride.replaceAll("[^A-Za-z0-9;.]", "")
            for(String userNamespace : userNamespaces.split(s_separator))
                namespaces.add(userNamespace)
        }
        else {
            for (SourceSet sourceSet : project.sourceSets) {
                for (File outDir : sourceSet.getOutput().getClassesDirs()) {
                    for (File subDir : outDir.listFiles()) {
                        if (subDir.isDirectory())
                            namespaces.addAll(getNamespacesForDirectory(subDir))
                    }
                }
            }
        }

        return namespaces
    }

    static String getNamespacesAsString(Project project) {
        //Allow user to override automatic namespace detection.
        String namespaceOverride = project.appscanSettings.namespaces;
        if(namespaceOverride != null && !namespaceOverride.equalsIgnoreCase("true"))
            return namespaceOverride.replaceAll("[^A-Za-z0-9;.]", "")

        String namespaces = ""

        for(String namespace : getNamespaces(project)) {
            namespaces += namespace + s_separator
        }

        return namespaces.endsWith(s_separator) ? namespaces.substring(0, namespaces.length() - 1) : namespaces
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
