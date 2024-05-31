/**
 * @ Copyright HCL Technologies Ltd. 2018, 2024.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.CoreConstants
import com.hcl.appscan.sdk.scan.IScanManager
import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.appscan.sdk.scanners.sast.SASTScanManager
import com.hcl.security.appscan.gradle.utils.PluginUtil
import org.gradle.api.Project

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
        Map<String, String> options = super.getOptions();
        options.put(CoreConstants.SCAN_NAME, getProject().appscanSettings.irxName)
        options.put(CoreConstants.APP_ID, getProject().appscanSettings.appId);
        options.put(SASTConstants.APPSCAN_IRGEN_CLIENT, "gradle"); //$NON-NLS-1$
        options.put(SASTConstants.APPSCAN_CLIENT_VERSION, project.getGradle().getGradleVersion());
        options.put(SASTConstants.IRGEN_CLIENT_PLUGIN_VERSION, PluginUtil.getPluginVersion());
        options.put(CoreConstants.ClientType, PluginUtil.getClientType());
        return options;
    }

    @Override
    protected IScanManager initScanManager() {
        m_scanManager = new SASTScanManager(getProject().appscanSettings.irxDir);
        m_scanManager.setIsSourceCodeOnlyEnabled(getProject().appscanSettings.sourceCodeOnly);
        m_scanManager.setIsOpenSourceOnlyEnabled(getProject().appscanSettings.openSourceOnly);
        m_scanManager.setIsStaticAnalysisOnlyEnabled(getProject().appscanSettings.staticAnalysisOnly);
        
        for(ITarget target : getTargets())
            m_scanManager.addScanTarget(target);
        return m_scanManager;
    }

    protected SASTScanManager getScanManager() {
        if(m_scanManager == null)
            initScanManager();
        return m_scanManager;
    }
}
