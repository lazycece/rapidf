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

package com.lazycece.rapidf.dispatcher.core.service;

import java.lang.reflect.Method;

/**
 * @author lazycece
 * @date 2023/6/28
 */
public class ServiceRegistration {

    /**
     * the service id
     */
    private String serviceId;

    /**
     * the service handler annotation
     */
    private ServiceHandler serviceHandler;

    /**
     * the service handler
     */
    private Handler<?, ?> handler;

    /**
     * the handler handle method
     */
    private Method method;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceHandler getServiceHandler() {
        return serviceHandler;
    }

    public void setServiceHandler(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    public Handler<?, ?> getHandler() {
        return handler;
    }

    public void setHandler(Handler<?, ?> handler) {
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
