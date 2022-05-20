package com.hcl.security.appscan.gradle.tasks

import com.hcl.security.appscan.gradle.actions.GenerateConfigRunner
import org.gradle.BuildAdapter

class GenerateConfigTask extends SecurityTask {

    @Override
    protected BuildAdapter getPostBuildAction() {
        return new GenerateConfigRunner(project, getTargets())
    }
}
