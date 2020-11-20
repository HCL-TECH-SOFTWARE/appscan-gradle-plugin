/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019, 2020.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.CoreConstants
import com.hcl.appscan.sdk.scan.IScanManager
import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.appscan.sdk.scanners.sast.SASTScanManager
import org.gradle.api.Project
import com.hcl.appscan.sdk.utils.SystemUtil

abstract class SASTSecurityAction extends SecurityAction {

    private SASTScanManager m_scanManager;

    public SASTSecurityAction(Project project, Collection<ITarget> targets) {
        super(project, targets);
        File irx = new File(project.appscanSettings.irxDir, project.appscanSettings.irxName + ".irx"); //$NON-NLS-1$
        if(irx.isFile())
            irx.delete();
    }

    @Override
    protected Map<String, String> getOptions() {
        String osName = SystemUtil.getOS();
        Map<String, String> options = new HashMap<String, String>()
        options.put(CoreConstants.SCAN_NAME, getProject().appscanSettings.irxName)
        options.put(CoreConstants.APP_ID, getProject().appscanSettings.appId);
        options.put("APPSCAN_IRGEN_CLIENT", "gradle");
        options.put("APPSCAN_CLIENT_VERSION", project.getGradle().getGradleVersion());
        options.put("IRGEN_CLIENT_PLUGIN_VERSION", getPluginVersion());
        options.put("ClientType", "gradle-" + osName + "-" + getPluginVersion());
        return options;
    }

    @Override
    protected IScanManager initScanManager() {
        m_scanManager = new SASTScanManager(getProject().appscanSettings.irxDir);
        for(ITarget target : getTargets())
            m_scanManager.addScanTarget(target);
        return m_scanManager;
    }

    protected SASTScanManager getScanManager() {
        if(m_scanManager == null)
            initScanManager();
        return m_scanManager;
    }

    private String getPluginVersion() {
        return this.getClass().getPackage().getImplementationVersion();
    }
}
