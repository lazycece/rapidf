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

package com.lazycece.rapidf.domain.event.impl;

import com.lazycece.rapidf.domain.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

/**
 * The default domain event publisher.
 *
 * @author lazycece
 * @date 2023/3/11
 * @see DomainEventPublisher
 * @see BeanPostProcessor
 */
public class DefaultDomainEventPublisher implements DomainEventPublisher, BeanPostProcessor {

    private final Logger log = LoggerFactory.getLogger(DefaultDomainEventPublisher.class);
    private final DomainEventDispatcher dispatcher = new DefaultDomainEventDispatcher();
    private DomainEventStore eventStore = new DefaultDomainEventStore();

    /**
     * @see DomainEventPublisher#publish
     */
    @Override
    public void publish(DomainEvent event) {
        this.eventStore.append(event);
        this.dispatcher.publish(event);
    }

    /**
     * @see BeanPostProcessor#postProcessBeforeInitialization
     */
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        registerSubscriber(bean);
        injectDomainEventStore(bean);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * To register event handle subscribe.
     *
     * @param bean bean object
     */
    private void registerSubscriber(Object bean) {
        boolean targetAnnotation = bean.getClass().isAnnotationPresent(EventHandler.class);
        boolean targetImpl = bean instanceof DomainEventHandler;
        if (targetAnnotation && targetImpl) {
            // Registry to the dispatcher
            log.info("To registry domain event handler, className={}", bean.getClass().getName());
            this.dispatcher.register((DomainEventHandler) bean);
        }
    }

    /**
     * To inject event store.
     *
     * @param bean bean object
     */
    private void injectDomainEventStore(Object bean) {
        boolean targetImpl = bean instanceof DomainEventStore;
        boolean defaultImpl = this.eventStore instanceof DefaultDomainEventStore;
        if (targetImpl && defaultImpl) {
            log.info("inject domain event store, class={}", bean.getClass().getName());
            this.eventStore = (DomainEventStore) bean;
        }
    }
}
