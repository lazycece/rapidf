/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.rapidf.example.config.log;

import com.lazycece.rapidf.logger.interceptor.LogInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lazycece
 * @date 2021/11/16
 */
@Configuration
public class LoggerConfig {

    @Bean
    public LogInterceptor logInterceptor() {
        LogInterceptor logInterceptor = new LogInterceptor();
        logInterceptor.setLogParser(new CustomLogParser());
        logInterceptor.setLogInfoClass(CustomLogInfo.class);
        return logInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("logController","helloService*");
        beanNameAutoProxyCreator.setInterceptorNames("logInterceptor");
        return beanNameAutoProxyCreator;
    }
}
