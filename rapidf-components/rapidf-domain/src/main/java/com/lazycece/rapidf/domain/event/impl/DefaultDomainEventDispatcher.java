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
import com.lazycece.rapidf.domain.event.exception.DomainEventException;
import com.lazycece.rapidf.domain.event.DomainEventHandler;
import com.lazycece.rapidf.domain.event.EventHandler;
import com.lazycece.rapidf.domain.event.EventHandlerRegistration;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * The default domain dispatcher.
 *
 * @author lazycece
 * @date 2023/3/11
 * @see DomainEventDispatcher
 */
public class DefaultDomainEventDispatcher implements DomainEventDispatcher {

    /**
     * The domain event registration table.
     */
    private final HashMap<String/*event type*/, List<EventHandlerRegistration>> registrationTable = new HashMap<>();

    /**
     * @see DomainEventDispatcher#register
     */
    @Override
    public void register(DomainEventHandler handler) {
        EventHandler annotation = handler.getClass().getAnnotation(EventHandler.class);
        if (annotation == null) {
            throw new DomainEventException("The domain event handler must be annotated with @EventHandler.");
        }
        String eventType = annotation.type();
        if (!StringUtils.hasText(eventType)) {
            throw new DomainEventException("The event type must be not blank.");
        }

        EventHandlerRegistration registration;
        Order order = handler.getClass().getAnnotation(Order.class);
        if (order != null) {
            registration = EventHandlerRegistration.build(handler.getClass().getName(), annotation, handler, order.value());
        } else {
            registration = EventHandlerRegistration.build(handler.getClass().getName(), annotation, handler);
        }

        if (!this.registrationTable.containsKey(eventType)) {
            this.registrationTable.put(eventType, new ArrayList<>());
        }
        this.registrationTable.get(eventType).add(registration);
    }

    /**
     * @see DomainEventDispatcher#publish
     */
    @Override
    public void publish(DomainEvent event) {
        String eventType = event.getType();
        if (!StringUtils.hasText(eventType)) {
            throw new DomainEventException("The event type must be not blank.");
        }

        List<EventHandlerRegistration> registrationList = this.registrationTable.get(eventType);
        if (registrationList == null) {
            // no event handler, return.
            return;
        }
        registrationList.forEach(registration -> {
            boolean matchCondition = match(registration.getAnnotation(), event);
            boolean accept = registration.getHandler().accept(event);
            if (matchCondition && accept) {
                registration.getHandler().handle(event);
            }
        });
    }

    /**
     * Do condition match.
     *
     * @param annotation  ${@link EventHandler}
     * @param domainEvent ${@link DomainEvent}
     * @return true/false
     */
    private boolean match(EventHandler annotation, DomainEvent domainEvent) {
        // match source
        if (StringUtils.hasText(annotation.source())) {
            if (!annotation.source().equals(domainEvent.getSource())) {
                return false;
            }
        }

        // match version
        if (StringUtils.hasText(annotation.version())) {
            if (!annotation.version().equals(domainEvent.getVersion())) {
                return false;
            }
        }

        // match extension
        if (annotation.extension().length > 0) {
            List<String> keyList = Arrays.asList(annotation.extension());
            if (domainEvent.getExtensions() == null || !domainEvent.getExtensions().keySet().containsAll(keyList)) {
                return false;
            }
        }

        // match expression
        if (StringUtils.hasText(annotation.expression())) {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(annotation.expression());
            Boolean result = exp.getValue(domainEvent, Boolean.class);
            return Boolean.TRUE.equals(result);
        }

        return true;
    }
}
