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

package com.lazycece.rapidf.dispatcher.core.facade;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lazycece
 * @date 2023/7/4
 */
public class FacadeRegistration {

    /**
     * The facade id
     */
    private String facadeId;
    /**
     * The facade service annotation
     */
    private FacadeService facadeService;
    /**
     * The facade bean object
     */
    private Object facade;
    /**
     * The facade action list
     */
    private List<FacadeAction> actionList;

    public String getFacadeId() {
        return facadeId;
    }

    public void setFacadeId(String facadeId) {
        this.facadeId = facadeId;
    }

    public FacadeService getFacadeService() {
        return facadeService;
    }

    public void setFacadeService(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    public Object getFacade() {
        return facade;
    }

    public void setFacade(Object facade) {
        this.facade = facade;
    }

    public List<FacadeAction> getActionList() {
        return actionList;
    }

    public void setActionList(List<FacadeAction> actionList) {
        this.actionList = actionList;
    }

    public static class FacadeAction {

        /**
         * The facade action id
         */
        private String action;

        /**
         * The facade action method
         */
        private Method method;
        /**
         * The facade action method request class
         */
        private Class<?> requestClass;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Class<?> getRequestClass() {
            return requestClass;
        }

        public void setRequestClass(Class<?> requestClass) {
            this.requestClass = requestClass;
        }
    }
}
