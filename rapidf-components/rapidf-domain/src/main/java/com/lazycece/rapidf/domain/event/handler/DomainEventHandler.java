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

import com.lazycece.rapidf.domain.event.DomainEvent;

/**
 * Domain event handler
 * <p>
 * To use it with ${@link EventHandler}
 *
 * @author lazycece
 * @date 2023/2/23
 */
public interface DomainEventHandler {

    /**
     * Accept condition process.
     *
     * @param event ${@link DomainEvent}
     * @return accept or not
     */
    default boolean accept(DomainEvent event) {
        return true;
    }

    /**
     * Do handle
     *
     * @param event ${@link DomainEvent}
     */
    void handle(DomainEvent event);
}
