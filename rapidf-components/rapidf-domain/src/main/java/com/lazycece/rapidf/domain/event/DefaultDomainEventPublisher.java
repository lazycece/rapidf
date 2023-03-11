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

package com.lazycece.rapidf.domain.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * The default domain event publisher.
 *
 * @author lazycece
 * @date 2023/3/11
 * @see DomainEventPublisher
 * @see BeanPostProcessor
 */
public class DefaultDomainEventPublisher implements DomainEventPublisher, BeanPostProcessor {

    private final DomainEventDispatcher dispatcher = new DefaultDomainEventDispatcher();

    /**
     * @see BeanPostProcessor#postProcessBeforeInitialization
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        boolean targetAnnotation = bean.getClass().isAnnotationPresent(EventHandler.class);
        boolean targetImpl = bean instanceof DomainEventHandler;
        if (targetAnnotation && targetImpl) {
            // Registry to the dispatcher
            this.dispatcher.register((DomainEventHandler) bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * @see DomainEventPublisher#publish
     */
    @Override
    public void publish(DomainEvent event) {
        this.dispatcher.publish(event);
    }
}
