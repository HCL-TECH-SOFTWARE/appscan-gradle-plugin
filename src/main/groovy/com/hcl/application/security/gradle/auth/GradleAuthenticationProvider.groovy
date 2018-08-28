/**
 * @ Copyright HCL Technologies Ltd. 2018.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.application.security.gradle.auth

import com.hcl.appscan.sdk.auth.AuthenticationHandler
import com.hcl.appscan.sdk.auth.IAuthenticationProvider
import com.hcl.appscan.sdk.auth.LoginType
import com.hcl.appscan.sdk.utils.SystemUtil
import org.apache.wink.json4j.JSONException

class GradleAuthenticationProvider implements IAuthenticationProvider {

    private String m_token = null;
    private String m_key;
    private String m_secret;

    public GradleAuthenticationProvider(String key, String secret) {
        m_key = key;
        m_secret = secret;
    }

    @Override
    boolean isTokenExpired() {
        boolean isExpired = false;
        AuthenticationHandler handler = new AuthenticationHandler(this);

        try {
            isExpired = handler.isTokenExpired() && !handler.login(m_key, m_secret, true, LoginType.ASoC_Federated);
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
        return SystemUtil.getDefaultServer();
    }

    @Override
    void saveConnection(String token) {
        m_token = token;
    }

    private String getToken() {
        return m_token ?: ""; //$NON-NLS-1$
    }
}
