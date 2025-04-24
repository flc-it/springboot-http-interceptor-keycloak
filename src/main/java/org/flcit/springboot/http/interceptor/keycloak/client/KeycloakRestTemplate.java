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

package org.flcit.springboot.http.interceptor.keycloak.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.flcit.springboot.http.client.core.configuration.HttpClientBuilder;
import org.flcit.springboot.http.client.rest.template.BaseRestTemplate;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@ConditionalOnBean(KeycloakOAuth2TokenInterceptor.class)
@ConfigurationProperties("oauth2.keycloak.http")
public class KeycloakRestTemplate extends BaseRestTemplate {

    /**
     * @param httpClientBuilder
     */
    public KeycloakRestTemplate(HttpClientBuilder httpClientBuilder) {
        super(httpClientBuilder);
    }

}