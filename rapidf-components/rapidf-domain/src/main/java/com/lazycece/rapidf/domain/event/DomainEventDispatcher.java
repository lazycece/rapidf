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

import com.lazycece.rapidf.domain.event.handler.DomainEventHandler;

/**
 * The domain event dispatcher.
 *
 * @author lazycece
 * @date 2023/2/23
 */
public interface DomainEventDispatcher {

    /**
     * Register the domain event handler.
     *
     * @param handler ${@link DomainEventHandler}
     */
    void register(DomainEventHandler handler);

    /**
     * To publish domain event.
     *
     * @param event ${@link DomainEvent}
     */
    void publish(DomainEvent event);
}
