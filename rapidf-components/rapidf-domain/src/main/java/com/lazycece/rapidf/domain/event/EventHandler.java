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

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Domain event handler annotation.
 * </p>
 * To use it must implement the ${@link DomainEventHandler} interface.
 *
 * @author lazycece
 * @date 2023/3/11
 * @see DomainEvent
 */
@Documented
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * The event source condition.
     * <p>
     * If equal, the condition is true otherwise false
     *
     * @return event source
     * @see DomainEvent#getSource()
     */
    String source();

    /**
     * The event type condition.
     * <p>
     * If equal, the condition is true otherwise false
     *
     * @return event type
     * @see DomainEvent#getType()
     */
    String type() default "";

    /**
     * The event version condition.
     * <p>
     * If equal, the condition is true otherwise false
     *
     * @return event version
     * @see DomainEvent#getVersion()
     */
    String version() default "";

    /**
     * The extension condition
     * <p>
     * If extension contains those keys, the condition is true otherwise false
     *
     * @return extension key
     * @see DomainEvent#getExtensions()
     */
    String[] extension() default {};

    /**
     * The SpEL condition.
     * <p>
     * If evaluation result is true, the condition is true otherwise false
     *
     * @return SpEL
     * @see org.springframework.expression.Expression
     */
    String expression() default "";
}
