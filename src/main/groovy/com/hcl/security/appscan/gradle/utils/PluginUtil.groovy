/**
 * @ Copyright HCL Technologies Ltd. 2022.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.utils

import com.hcl.appscan.sdk.utils.SystemUtil

class PluginUtil {

    static String getClientType() {
        return "gradle-" + SystemUtil.getOS() + "-" + getPluginVersion();
    }

    static String getPluginVersion() {
        return PluginUtil.class.getPackage().getImplementationVersion();
    }
}
