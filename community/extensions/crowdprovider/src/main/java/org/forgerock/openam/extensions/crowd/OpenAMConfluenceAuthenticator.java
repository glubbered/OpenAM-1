/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010 ForgeRock AS. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://forgerock.org/license/CDDLv1.0.html
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://forgerock.org/license/CDDLv1.0.html
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * 
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 */
package org.forgerock.openam.extensions.crowd;

import com.atlassian.confluence.user.ConfluenceAuthenticator;
import com.atlassian.seraph.auth.AuthenticatorException;
import com.atlassian.seraph.config.SecurityConfigFactory;
import com.atlassian.seraph.util.RedirectUtils;
import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Category;

/**
 *
 * @author steve
 */
public class OpenAMConfluenceAuthenticator extends ConfluenceAuthenticator {
    private static final Category log = Category.getInstance(OpenAMConfluenceAuthenticator.class);

    @Override
    public Principal getUser(HttpServletRequest request, HttpServletResponse response) {
        Principal user = null;

        try {
            request.getSession(true);
            log.info("Trying seamless Single Sign-on...");
            String username = obtainUsername(request);
            log.info("Got username = " + username);
            if (username != null) {
                if (request.getSession() != null && request.getSession().getAttribute(LOGGED_IN_KEY) != null) {
                    log.info("Session found; user already logged in");
                    user = (Principal) request.getSession().getAttribute(LOGGED_IN_KEY);
                } else {
                    user = getUser(username);
                    log.info("Logged in via SSO, with User " + user);
                    request.getSession().setAttribute(LOGGED_IN_KEY, user);
                    request.getSession().setAttribute(LOGGED_OUT_KEY, null);
                }
            } else {
                String redirectUrl = RedirectUtils.getLoginUrl(request);
                log.info("Username is null; redirecting to " + redirectUrl);
                // user was not found, or not currently valid
                // response.sendRedirect(redirectUrl);
                return null;
            }
        } catch (Exception ex) {
            log.warn("Exception: " + ex, ex);
        }
        
        return user;

    }
    private SSOToken getToken(HttpServletRequest request) {
        SSOToken token = null;
        
        try {
            SSOTokenManager manager = SSOTokenManager.getInstance();
            token = manager.createSSOToken(request);
        } catch (Exception ex) {
            log.debug("Error creating SSOToken", ex);
        }
        
        return token;
    }

    private boolean isTokenValid(SSOToken token) {
        boolean result = false;
        
        try {
            SSOTokenManager manager = SSOTokenManager.getInstance();
            result = manager.isValidToken(token);
        } catch (Exception ex) {
            log.debug("Error validating SSOToken", ex);
        }
        
        return result;
    }

    private String obtainUsername(HttpServletRequest request) {
        String result = null;
        SSOToken token = getToken(request);
        
        if (token != null && isTokenValid(token)) {
            try {
                result = token.getProperty("UserId");
            } catch (SSOException ssoe) {
                log.error("Error getting UserId from SSOToken", ssoe);
            }
        }
        return result;
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) 
    throws AuthenticatorException {
        boolean result = false;
        
        try {
            result = doLogout(request, response);
        } catch (Exception ex) {
            log.warn("Exception: " + ex, ex);
        }
        
        return result;
    }

    private boolean doLogout(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticatorException, IOException {
        if (super.logout(request, response)) {
            logoutOfOpenSSO(response);
            return true;
        }
        
        return false;
    }

    private void logoutOfOpenSSO(HttpServletResponse response)
    throws IOException {
        String logoutURL = SecurityConfigFactory.getInstance().getLogoutURL();
        
        if (logoutURL != null) {
            response.sendRedirect(logoutURL);
        }
    }    
}
