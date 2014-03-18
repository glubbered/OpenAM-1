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
 * Copyright 2014 ForgeRock AS. All rights reserved.
 */

package org.forgerock.openam.sts.config.user;

import org.forgerock.openam.sts.AMSTSConstants;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;

import static org.testng.Assert.assertTrue;

public class STSInstanceConfigTest {
    private static final String JSON_BASE = "json";
    private static final String AM_DEPLOYMENT = "am_depl";
    private static final String AUTH = "rest_auth";
    private static final String ID_FROM_SESSION = "id_from_session";
    private static final String LOGOUT = "logout";
    private static final String COOKIE = "cookie";
    private static final String ISSUER = "cornholio";
    private static final String KEYSTORE = "keystore_stuff";

    @Test
    public void testSettings() throws UnsupportedEncodingException {
        STSInstanceConfig instance = buildConfig();
        assertTrue(JSON_BASE.equals(instance.getJsonRestBase()));
        assertTrue(AM_DEPLOYMENT.equals(instance.getAMDeploymentUrl()));
        assertTrue(AUTH.equals(instance.getAMRestAuthNUriElement()));
        assertTrue(ID_FROM_SESSION.equals(instance.getAMRestIdFromSessionUriElement()));
        assertTrue(LOGOUT.equals(instance.getAMRestLogoutUriElement()));
        assertTrue(COOKIE.equals(instance.getAMSessionCookieName()));
        assertTrue(ISSUER.equals(instance.getIssuerName()));
    }

    @Test
    public void testJsonRoundTrip() throws UnsupportedEncodingException {
        STSInstanceConfig instance = buildConfig();
        STSInstanceConfig secondInstance = STSInstanceConfig.fromJson(instance.toJson());
        assertTrue(instance.equals(secondInstance));
    }

    private STSInstanceConfig buildConfig() throws UnsupportedEncodingException {
        KeystoreConfig keystoreConfig =
                KeystoreConfig.builder()
                        .fileName(KEYSTORE)
                        .password(KEYSTORE.getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                        .encryptionKeyAlias(KEYSTORE)
                        .signatureKeyAlias(KEYSTORE)
                        .encryptionKeyPassword(KEYSTORE.getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                        .signatureKeyPassword(KEYSTORE.getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                        .build();

        return STSInstanceConfig.builder()
                .amJsonRestBase(JSON_BASE)
                .amDeploymentUrl(AM_DEPLOYMENT)
                .amRestAuthNUriElement(AUTH)
                .amRestIdFromSessionUriElement(ID_FROM_SESSION)
                .amRestLogoutUriElement(LOGOUT)
                .amSessionCookieName(COOKIE)
                .issuerName(ISSUER)
                .keystoreConfig(keystoreConfig)
                .build();
    }
}