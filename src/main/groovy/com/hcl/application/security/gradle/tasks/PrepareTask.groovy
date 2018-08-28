/**
 * @ Copyright HCL Technologies Ltd. 2018.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.application.security.gradle.tasks

import com.hcl.application.security.gradle.actions.PrepareRunner
import org.gradle.BuildAdapter

class PrepareTask extends SecurityTask {

    @Override
    protected BuildAdapter getPostBuildAction() {
        return new PrepareRunner(project, getTargets());
    }
}
