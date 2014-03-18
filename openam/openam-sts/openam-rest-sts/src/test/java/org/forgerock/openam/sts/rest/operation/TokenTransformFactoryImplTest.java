/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyrighted [year] [name of copyright owner]".
 *
 * Copyright © 2013-2014 ForgeRock AS. All rights reserved.
 */

package org.forgerock.openam.sts.rest.operation;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import org.forgerock.openam.sts.AMSTSConstants;
import org.forgerock.openam.sts.STSInitializationException;
import org.forgerock.openam.sts.TokenType;
import org.forgerock.openam.sts.rest.config.user.TokenTransformConfig;
import org.forgerock.openam.sts.token.ThreadLocalAMTokenCache;
import org.forgerock.openam.sts.token.ThreadLocalAMTokenCacheImpl;
import org.forgerock.openam.sts.token.UrlConstituentCatenator;
import org.forgerock.openam.sts.token.UrlConstituentCatenatorImpl;
import org.forgerock.openam.sts.token.provider.AMTokenProvider;
import org.forgerock.openam.sts.token.validator.wss.UsernameTokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.inject.Named;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class TokenTransformFactoryImplTest {
    static final boolean INVALIDATE_INTERIM_AM_SESSIONS = true;
    TokenTransformFactory transformFactory;
    static class MyModule extends AbstractModule {
        @Override
        protected void configure() {
            bindConstant().annotatedWith(Names.named(AMSTSConstants.AM_DEPLOYMENT_URL)).to("faux_deployment_url");
            bindConstant().annotatedWith(Names.named(AMSTSConstants.REST_LOGOUT_URI_ELEMENT)).to("faux_logout_url");
            bindConstant().annotatedWith(Names.named(AMSTSConstants.REST_ID_FROM_SESSION_URI_ELEMENT)).to("faux_id_from_session_url");
            bindConstant().annotatedWith(Names.named(AMSTSConstants.AM_SESSION_COOKIE_NAME)).to("faux_cookie_name");
            bind(ThreadLocalAMTokenCache.class).to(ThreadLocalAMTokenCacheImpl.class);
            bind(UrlConstituentCatenator.class).to(UrlConstituentCatenatorImpl.class);
            bind(TokenTransformFactory.class).to(TokenTransformFactoryImpl.class);
        }

        @Provides
        UsernameTokenValidator getUsernameTokenValidator() {
            return null;
        }

        @Provides
        AMTokenProvider getAMTokenProvider() {
            return null;
        }

        @Provides
        @Named(AMSTSConstants.REALM)
        String realm() {
            return "bobo";
        }

        @Provides
        @Named(AMSTSConstants.AM_REST_AUTHN_JSON_ROOT)
        String getJsonRoot() {
            return "json";
        }

        @Provides
        Logger getSlf4jLogger() {
            return LoggerFactory.getLogger(AMSTSConstants.REST_STS_DEBUG_ID);
        }

    }

    @BeforeTest
    public void initialize() {
         transformFactory = Guice.createInjector(new MyModule()).getInstance(TokenTransformFactory.class);
    }

    @Test
    public void testTransformCreation() throws STSInitializationException {
        TokenTransformConfig ttc = new TokenTransformConfig(TokenType.USERNAME, TokenType.SAML2, INVALIDATE_INTERIM_AM_SESSIONS);
        TokenTransform transform = transformFactory.buildTokenTransform(ttc);
        assertTrue(transform.isTransformSupported(TokenType.USERNAME, TokenType.SAML2));
        assertFalse(transform.isTransformSupported(TokenType.OPENAM, TokenType.SAML2));

        ttc = new TokenTransformConfig(TokenType.OPENAM, TokenType.SAML2, INVALIDATE_INTERIM_AM_SESSIONS);
        transform = transformFactory.buildTokenTransform(ttc);
        assertTrue(transform.isTransformSupported(TokenType.OPENAM, TokenType.SAML2));
        assertFalse(transform.isTransformSupported(TokenType.USERNAME, TokenType.SAML2));

    }

    @Test(expectedExceptions = STSInitializationException.class)
    public void testIncorrectTransformCreation() throws STSInitializationException {
        TokenTransformConfig ttc = new TokenTransformConfig(TokenType.USERNAME, TokenType.USERNAME, INVALIDATE_INTERIM_AM_SESSIONS);
        transformFactory.buildTokenTransform(ttc);
    }
}