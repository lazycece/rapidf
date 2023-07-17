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

package com.lazycece.rapidf.dispatcher.sample.facade;

import com.lazycece.rapidf.dispatcher.core.facade.FacadeService;
import com.lazycece.rapidf.dispatcher.sample.dto.request.OrderCancelRequest;
import com.lazycece.rapidf.dispatcher.sample.dto.request.OrderCreateRequest;
import com.lazycece.rapidf.dispatcher.sample.dto.response.OrderCancelResponse;
import com.lazycece.rapidf.dispatcher.sample.dto.response.OrderCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author lazycece
 * @date 2023/7/17
 */
@Slf4j
@Service
@FacadeService(name = "order", version = "1.0.0")
public class OrderCommandFacadeImpl implements OrderCommandFacade {

    @Override
    public OrderCreateResponse create(OrderCreateRequest request) {
        log.info("================== service handler: order_create");

        OrderCreateResponse response = new OrderCreateResponse();
        response.setOrderId(UUID.randomUUID().toString());
        return response;
    }

    @Override
    public OrderCancelResponse cancel(OrderCancelRequest request) {
        log.info("================== service handler: order_cancel");

        return new OrderCancelResponse();
    }
}
