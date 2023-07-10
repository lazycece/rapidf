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

package com.lazycece.rapidf.dispatcher.core.service;

import com.lazycece.rapidf.dispatcher.configuration.DispatcherProperties;
import com.lazycece.rapidf.dispatcher.core.DispatchCmd;
import com.lazycece.rapidf.dispatcher.core.DispatchException;
import com.lazycece.rapidf.dispatcher.core.Dispatcher;
import com.lazycece.rapidf.dispatcher.core.DispatchRequestParser;
import com.lazycece.rapidf.dispatcher.constants.DispatcherConstants;
import com.lazycece.rapidf.dispatcher.utils.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service dispatch, for ${@link Handler}.
 *
 * @author lazycece
 * @date 2023/7/2
 * @see Dispatcher,BeanPostProcessor
 */
@Component
@ConditionalOnBean(DispatchRequestParser.class)
@ConditionalOnProperty(prefix = "rapidf.dispatcher", name = "pattern", havingValue = "service")
public class ServiceDispatcher implements Dispatcher, BeanPostProcessor {

    private final Logger log = LoggerFactory.getLogger(ServiceDispatcher.class);

    /**
     * the service handler registration table
     */
    private final ConcurrentHashMap<String, ServiceRegistration> registrationTable = new ConcurrentHashMap<>();

    @Autowired
    private DispatchRequestParser dispatchRequestParser;
    @Autowired
    private DispatcherProperties dispatcherProperties;

    /**
     * @see Dispatcher#dispatch
     */
    @Override
    public Object dispatch(DispatchCmd cmd) {
        Assert.notNull(cmd, "service dispatcher executing, command is null");
        ServiceCmd serviceCmd = (ServiceCmd) cmd;
        String serviceId = serviceCmd.getName() + serviceCmd.getVersion();
        ServiceRegistration serviceRegistration = registrationTable.get(serviceId);
        if (serviceRegistration == null) {
            throw new DispatchException("service not found !");
        }

        Object request = dispatchRequestParser.parse(serviceCmd.getRequest(), serviceRegistration.getServiceHandler().requestClass());

        if (dispatcherProperties.isValidateRequest()) {
            ValidateUtils.validate(request);
        }

        try {
            return serviceRegistration.getMethod().invoke(serviceRegistration.getHandler(), request);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DispatchException("service invoke error.");
        }
    }


    /**
     * @see BeanPostProcessor#postProcessAfterInitialization
     */
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        registerServiceHandler(bean);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * To register the service handler
     *
     * @param bean spring bean
     */
    private void registerServiceHandler(Object bean) {
        boolean targetAnnotation = bean.getClass().isAnnotationPresent(ServiceHandler.class);
        boolean targetImpl = bean instanceof Handler<?, ?>;
        if (targetAnnotation && targetImpl) {
            log.info("To registry service handler, className={}", bean.getClass().getName());
            ServiceRegistration serviceRegistration = buildServiceRegistration((Handler<?, ?>) bean);
            this.registrationTable.put(serviceRegistration.getServiceId(), serviceRegistration);
        }
    }

    /**
     * Assemble service registration information.
     *
     * @param handler handler implement class
     * @return see ${@link ServiceRegistration}
     */
    private ServiceRegistration buildServiceRegistration(Handler<?, ?> handler) {
        ServiceHandler serviceHandler = handler.getClass().getAnnotation(ServiceHandler.class);
        String serviceId = serviceHandler.name() + serviceHandler.version();
        Method method;
        try {
            method = handler.getClass().getMethod(DispatcherConstants.SERVICE_HANDLER_METHOD_NAME, serviceHandler.requestClass());
        } catch (NoSuchMethodException e) {
            throw new DispatchException(String.format("no match service '%s' method.", DispatcherConstants.SERVICE_HANDLER_METHOD_NAME));
        }

        ServiceRegistration serviceRegistration = new ServiceRegistration();
        serviceRegistration.setServiceId(serviceId);
        serviceRegistration.setServiceHandler(serviceHandler);
        serviceRegistration.setHandler(handler);
        serviceRegistration.setMethod(method);
        return serviceRegistration;
    }
}
