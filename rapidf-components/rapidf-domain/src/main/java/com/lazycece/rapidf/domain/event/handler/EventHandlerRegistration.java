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

package com.lazycece.rapidf.domain.event.handler;

/**
 * The domain event handler registration.
 *
 * @author lazycece
 * @date 2023/3/12
 */
public class EventHandlerRegistration {

    /**
     * The event handler name.
     */
    private String name;
    /**
     * The event handler annotation.
     *
     * @see EventHandler
     */
    private EventHandler annotation;
    /**
     * The event handler.
     *
     * @see DomainEventHandler
     */
    private DomainEventHandler handler;
    /**
     * The event handler order.
     */
    private int order = 0;

    public EventHandlerRegistration() {
    }

    public EventHandlerRegistration(EventHandler annotation, DomainEventHandler handler) {
        this.name = handler.getClass().getName();
        this.annotation = annotation;
        this.handler = handler;
    }

    public EventHandlerRegistration(EventHandler annotation, DomainEventHandler handler, int order) {
        this.name = handler.getClass().getName();
        this.annotation = annotation;
        this.handler = handler;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventHandler getAnnotation() {
        return annotation;
    }

    public void setAnnotation(EventHandler annotation) {
        this.annotation = annotation;
    }

    public DomainEventHandler getHandler() {
        return handler;
    }

    public void setHandler(DomainEventHandler handler) {
        this.handler = handler;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
