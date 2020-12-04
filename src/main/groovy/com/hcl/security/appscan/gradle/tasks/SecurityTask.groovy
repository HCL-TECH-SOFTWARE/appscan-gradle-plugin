/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.tasks

import com.hcl.security.appscan.gradle.error.ASoCException
import com.hcl.security.appscan.gradle.handlers.IPrepareHandler
import com.hcl.security.appscan.gradle.handlers.PrepareHandlerFactory
import com.hcl.appscan.sdk.scan.ITarget
import org.gradle.BuildAdapter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException

abstract class SecurityTask extends DefaultTask {

    private static Collection<ITarget> m_targets = new HashSet<ITarget>();

    private static boolean isAddedToPostBuildAction = false;

    @InputFiles
    def inputfiles

    @TaskAction
    def createTargets() {
        if (isAddedToPostBuildAction == false) {
            project.getGradle().addBuildListener(getPostBuildAction());
            isAddedToPostBuildAction = true;
        }

        try {
            IPrepareHandler handler = PrepareHandlerFactory.createHandler(project);
            m_targets.addAll(handler.getTargets());
        } catch(ASoCException e) {
            throw new TaskExecutionException(this, e);
        }
    }

    public static void actionsWhenBuildFinished() {
        m_targets.clear(); // specifically to fix gradle daemon issue
        isAddedToPostBuildAction = false;
    }

    protected Collection<ITarget> getTargets() {
        return m_targets;
    }

    protected abstract BuildAdapter getPostBuildAction();
}
