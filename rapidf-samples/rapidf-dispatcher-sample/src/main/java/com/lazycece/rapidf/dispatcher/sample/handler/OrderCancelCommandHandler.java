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

package com.lazycece.rapidf.dispatcher.sample.handler;

import com.lazycece.rapidf.dispatcher.core.service.Handler;
import com.lazycece.rapidf.dispatcher.core.service.ServiceHandler;
import com.lazycece.rapidf.dispatcher.sample.dto.request.OrderCancelRequest;
import com.lazycece.rapidf.dispatcher.sample.dto.response.OrderCancelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lazycece
 * @date 2023/7/17
 */
@Slf4j
@Component
@ServiceHandler(name = "order_cancel", version = "1.0.0")
public class OrderCancelCommandHandler implements Handler<OrderCancelResponse, OrderCancelRequest> {

    @Override
    public OrderCancelResponse handle(OrderCancelRequest orderCancelRequest) {
        log.info("================== service handler: order_cancel");

        return new OrderCancelResponse();
    }
}
