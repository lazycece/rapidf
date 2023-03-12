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

package com.lazycece.rapidf.domain.sample.event.handler;

import com.lazycece.rapidf.domain.event.DomainEvent;
import com.lazycece.rapidf.domain.event.handler.DomainEventHandler;
import com.lazycece.rapidf.domain.event.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * @author lazycece
 * @date 2023/3/12
 */
@Order(5)
@EventHandler(type = "com.lazycece.rapidf.domain.sample.event.model.OrderDomainEvent")
public class OrderLifeCycleHandler implements DomainEventHandler {

    private final Logger log = LoggerFactory.getLogger(OrderLifeCycleHandler.class);

    @Override
    public void handle(DomainEvent event) {
        log.info("========= OrderLifeCycleHandler, order=5 ============");

    }
}
