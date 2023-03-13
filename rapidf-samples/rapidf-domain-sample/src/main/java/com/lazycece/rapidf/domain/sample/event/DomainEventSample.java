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

package com.lazycece.rapidf.domain.sample.event;

import com.lazycece.rapidf.domain.event.DomainEvent;
import com.lazycece.rapidf.domain.event.DomainEventBuilder;
import com.lazycece.rapidf.domain.event.DomainEventPublisher;
import com.lazycece.rapidf.domain.sample.event.model.OrderDomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author lazycece
 * @date 2023/3/12
 */
@Component
public class DomainEventSample implements CommandLineRunner {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    public void run(String... args) {

        OrderDomainEvent data = new OrderDomainEvent();
        data.setUserId(1L);
        data.setAmount(BigDecimal.valueOf(10));
        data.setGoodsId("goodsId");
        data.setStatus("success");
        DomainEvent domainEvent = DomainEventBuilder.builder()
                .type(OrderDomainEvent.class.getName())
                .source("local")
                .data(data)
                .extension("tag1", true)
                .extension("tag2", true)
                .build();

        domainEventPublisher.publish(domainEvent);
    }
}
