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

package com.lazycece.rapidf.dispatcher.core.facade;

import com.lazycece.rapidf.dispatcher.configuration.DispatcherProperties;
import com.lazycece.rapidf.dispatcher.core.DispatchCmd;
import com.lazycece.rapidf.dispatcher.exception.DispatchException;
import com.lazycece.rapidf.dispatcher.core.DispatchRequestParser;
import com.lazycece.rapidf.dispatcher.core.Dispatcher;
import com.lazycece.rapidf.dispatcher.helper.FacadeHelper;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Facade dispatch
 *
 * @author lazycece
 * @date 2023/7/2
 * @see Dispatcher,BeanPostProcessor
 */
@Component
@ConditionalOnBean(value = {DispatchRequestParser.class, DispatcherProperties.class})
@ConditionalOnProperty(prefix = "rapidf.dispatcher", name = "pattern", havingValue = "facade")
public class FacadeDispatcher implements Dispatcher, BeanPostProcessor {

    private final Logger log = LoggerFactory.getLogger(FacadeDispatcher.class);

    /**
     * the facade service registration table
     */
    private final ConcurrentHashMap<String, FacadeRegistration> registrationTable = new ConcurrentHashMap<>();

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
        FacadeCmd facadeCmd = (FacadeCmd) cmd;
        String facadeId = facadeCmd.getName() + facadeCmd.getVersion();
        FacadeRegistration facadeRegistration = registrationTable.get(facadeId);
        if (facadeRegistration == null) {
            throw new DispatchException("facade not found !");
        }
        FacadeRegistration.FacadeAction facadeAction = facadeRegistration.getActionMap().get(facadeCmd.getAction());
        if (facadeAction == null) {
            throw new DispatchException("facade action not found !");
        }

        Object request = dispatchRequestParser.parse(facadeCmd.getRequest(), facadeAction.getRequestClass());

        if (dispatcherProperties.isValidateRequest()) {
            ValidateUtils.validate(request);
        }

        try {
            return facadeAction.getMethod().invoke(facadeRegistration.getFacade(), request);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DispatchException("facade action invoke error.");
        }
    }

    /**
     * @see BeanPostProcessor#postProcessAfterInitialization
     */
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        registerFacadeService(bean);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * To register the facade service
     *
     * @param bean spring bean
     */
    private void registerFacadeService(Object bean) {
        boolean targetAnnotation = bean.getClass().isAnnotationPresent(FacadeService.class);
        boolean targetFacade = FacadeHelper.isExpectedFacade(bean.getClass());
        if (targetAnnotation && targetFacade) {
            log.info("To registry facade service, className={}", bean.getClass().getName());
            FacadeRegistration facadeRegistration = buildFacadeRegistration((Facade) bean);
            this.registrationTable.put(facadeRegistration.getFacadeId(), facadeRegistration);
        }
    }

    /**
     * Assemble facade service registration information.
     *
     * @param facade facade implement class
     * @return see ${@link FacadeRegistration}
     */
    private FacadeRegistration buildFacadeRegistration(Facade facade) {
        FacadeService facadeService = facade.getClass().getAnnotation(FacadeService.class);
        String facadeId = facadeService.name() + facadeService.version();
        Map<String, FacadeRegistration.FacadeAction> actionMap = FacadeHelper.getFacadeActionMap(facade.getClass());

        FacadeRegistration facadeRegistration = new FacadeRegistration();
        facadeRegistration.setFacadeId(facadeId);
        facadeRegistration.setFacadeService(facadeService);
        facadeRegistration.setFacade(facade);
        facadeRegistration.setActionMap(actionMap);

        return facadeRegistration;
    }
}
