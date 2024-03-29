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
import com.lazycece.rapidf.domain.sample.event.model.OrderDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * @author lazycece
 * @date 2023/3/12
 */
@Order(3)
@EventHandler(
        type = "com.lazycece.rapidf.domain.sample.event.model.OrderDomainEvent"
        , source = "local"
        , version = "1.0.0"
        , extension = {"tag1", "tag2"}
        , expression = "{'success'.equals(data.status)}")
public class OrderSuccessEventHandler implements DomainEventHandler {

    private final Logger log = LoggerFactory.getLogger(OrderSuccessEventHandler.class);

    @Override
    public void handle(DomainEvent event) {
        log.info("========= OrderSuccessEventHandler, order=3 ============");
        OrderDomainEvent data = event.getData(OrderDomainEvent.class);
        log.info("========= OrderSuccessEventHandler, order=3, id4={} ============", data.getOrderId());
    }
}
