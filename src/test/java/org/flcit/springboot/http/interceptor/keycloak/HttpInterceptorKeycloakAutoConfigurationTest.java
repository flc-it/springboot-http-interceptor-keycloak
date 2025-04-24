/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flcit.springboot.http.interceptor.keycloak;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import org.flcit.springboot.commons.test.util.ContextRunnerUtils;
import org.flcit.springboot.commons.test.util.PropertyTestUtils;
import org.flcit.springboot.commons.test.util.ReflectionTestUtils;
import org.flcit.springboot.http.client.core.HttpClientCoreAutoConfiguration;
import org.flcit.springboot.http.client.core.interceptor.oauth2.OAuth2GrantType;
import org.flcit.springboot.http.interceptor.keycloak.client.KeycloakOAuth2TokenInterceptor;
import org.flcit.springboot.http.interceptor.keycloak.client.KeycloakRestTemplate;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
class HttpInterceptorKeycloakAutoConfigurationTest {

    private static final String PREFIX_PROPERTY = "oauth2.keycloak.client.";
    private static final String URL = "https://localhost/auth/realms/royaume/protocol/openid-connect/token";
    private static final String CLIENT_ID = "id";
    private static final String CLIENT_SECRET = "secret";

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    HttpClientCoreAutoConfiguration.class,
                    HttpInterceptorKeycloakAutoConfiguration.class));

    @Test
    void noBeans() {
        ContextRunnerUtils.assertDoesNotHaveBean(this.contextRunner, KeycloakOAuth2TokenInterceptor.class, KeycloakRestTemplate.class);
    }

    @Test
    void beansOk() {
        this.contextRunner
        .withPropertyValues(
                PropertyTestUtils.getValue(PREFIX_PROPERTY, "url", URL),
                PropertyTestUtils.getValue(PREFIX_PROPERTY, "client-id", CLIENT_ID),
                PropertyTestUtils.getValue(PREFIX_PROPERTY, "client-secret", CLIENT_SECRET)
         )
        .run(contexte -> {
            assertThat(contexte).hasSingleBean(KeycloakOAuth2TokenInterceptor.class);
            assertThat(contexte).hasSingleBean(KeycloakRestTemplate.class);
            final KeycloakOAuth2TokenInterceptor interceptor = contexte.getBean(KeycloakOAuth2TokenInterceptor.class);
            assertEquals(URL, ReflectionTestUtils.getFieldValue(interceptor, "url"));
            assertEquals(CLIENT_ID, ReflectionTestUtils.getFieldValue(interceptor, "clientId"));
            assertEquals(CLIENT_SECRET, ReflectionTestUtils.getFieldValue(interceptor, "clientSecret"));
            assertEquals(OAuth2GrantType.client_credentials, ReflectionTestUtils.getFieldValue(interceptor, "grantType"));
            assertNull(ReflectionTestUtils.getFieldValue(interceptor, "scope"));
            assertEquals(contexte.getBean(KeycloakRestTemplate.class), interceptor.getRestTemplate());
            assertFalse(interceptor.isAuthentification(URI.create(URL)));
            assertFalse(interceptor.isAuthentification(null));
        });
    }

}
