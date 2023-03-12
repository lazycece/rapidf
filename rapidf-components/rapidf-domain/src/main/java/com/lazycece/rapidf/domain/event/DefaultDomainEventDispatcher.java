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

import com.lazycece.rapidf.domain.event.exception.DomainEventException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The default domain dispatcher.
 *
 * @author lazycece
 * @date 2023/3/11
 * @see DomainEventDispatcher
 */
public class DefaultDomainEventDispatcher implements DomainEventDispatcher {

    /**
     * The domain event subscribers.
     */
    private final Map<String/*event type*/, Map<EventHandler, DomainEventHandler>> subscribers = new HashMap<>();

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
        if (!this.subscribers.containsKey(eventType)) {
            this.subscribers.put(eventType, new HashMap<>());
        }
        this.subscribers.get(eventType).put(annotation, handler);
    }

    /**
     * @see DomainEventDispatcher#publish
     */
    @Override
    public void publish(DomainEvent event) {
        String eventType = event.getType();
        if (StringUtils.hasText(eventType)) {
            throw new DomainEventException("The event type must be not blank.");
        }
        Map<EventHandler, DomainEventHandler> handlerMap = this.subscribers.get(eventType);
        if (handlerMap == null) {
            // no event handler, return.
            return;
        }
        handlerMap.forEach((annotation, handler) -> {
            // TODO: 2023/3/12  how to  control order.
            if (match(annotation, event) && handler.accept(event)) {
                handler.handle(event);
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
