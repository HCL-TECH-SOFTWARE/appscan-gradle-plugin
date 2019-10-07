/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.handlers

import com.hcl.appscan.sdk.scan.ITarget

interface IPrepareHandler {

    List<ITarget> getTargets()
}