/*
 *    Copyright 2023 lazycece<lazycece@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lazycece.rapidf.auapi.configuration;

import com.lazycece.au.AuManager;
import com.lazycece.au.api.spring.boot.autoconfigure.AuApiProperties;
import com.lazycece.rapidf.auapi.filter.SubjectFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author lazycece
 * @date 2023/3/20
 */
@Configuration
@ComponentScan(basePackages = "com.lazycece.rapidf.auapi")
public class AuApiConfiguration {

    @Bean
    @ConditionalOnBean(AuApiProperties.class)
    @ConditionalOnProperty(prefix = "au.api.token", name = "enable", havingValue = "true")
    public SubjectFilter subjectFilter(AuApiProperties auApiProperties) {
        AuApiProperties.AuApiToken apiToken = auApiProperties.getToken();
        SubjectFilter subjectFilter = new SubjectFilter();
        AuManager.getInstance().addAuFilter(subjectFilter)
                .includePatterns(apiToken.getIncludePatterns())
                .excludePatterns(apiToken.getExcludePatterns());
        return subjectFilter;
    }

}
