/**
 * @ Copyright HCL Technologies Ltd. 2018, 2024.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.auth

import com.hcl.appscan.sdk.auth.AuthenticationHandler
import com.hcl.appscan.sdk.auth.IAuthenticationProvider
import com.hcl.appscan.sdk.auth.LoginType
import com.hcl.appscan.sdk.utils.SystemUtil
import com.hcl.security.appscan.gradle.utils.PluginUtil
import org.apache.wink.json4j.JSONException

class GradleAuthenticationProvider implements IAuthenticationProvider {

    private String m_token = null;
    private String m_key;
    private String m_secret;
    private String m_serviceUrl = null;
    private boolean m_acceptssl = false;

    public GradleAuthenticationProvider(String key, String secret) {
        this(key, secret, null, false);
    }

    public GradleAuthenticationProvider(String key, String secret, String serviceUrl, boolean acceptssl) {
        m_key = key;
        m_secret = secret;
        m_serviceUrl = serviceUrl;
        m_acceptssl = acceptssl;
    }

    @Override
    boolean isTokenExpired() {
        boolean isExpired = false;
        AuthenticationHandler handler = new AuthenticationHandler(this);

        try {
            isExpired = handler.isTokenExpired() && !handler.login(m_key, m_secret, true, LoginType.ASoC_Federated, PluginUtil.getClientType());
        } catch (IOException | JSONException e) {
            isExpired = false;
        }
        return isExpired;
    }

    @Override
    Map<String, String> getAuthorizationHeader(boolean persist) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer "+ getToken().trim()); //$NON-NLS-1$ //$NON-NLS-2$
        if(persist)
            headers.put("Connection", "Keep-Alive"); //$NON-NLS-1$ //$NON-NLS-2$
        return headers;
    }

    @Override
    String getServer() {
        return m_serviceUrl == null || m_serviceUrl.trim().isEmpty() ? SystemUtil.getServer(m_key) : m_serviceUrl;
    }

    @Override
    void saveConnection(String token) {
        m_token = token;
    }

    @Override
    Proxy getProxy() {
        return Proxy.NO_PROXY;
    }

    @Override
    public boolean getacceptInvalidCerts() {
        return m_acceptssl;
    }

    private String getToken() {
        return m_token ?: ""; //$NON-NLS-1$
    }
}
