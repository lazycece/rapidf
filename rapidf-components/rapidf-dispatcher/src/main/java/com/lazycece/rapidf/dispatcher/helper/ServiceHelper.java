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

package com.lazycece.rapidf.dispatcher.helper;

import com.lazycece.rapidf.dispatcher.core.service.CommandHandler;
import com.lazycece.rapidf.dispatcher.core.service.Handler;
import com.lazycece.rapidf.dispatcher.core.service.QueryHandler;
import com.lazycece.rapidf.dispatcher.exception.DispatchException;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lazycece
 * @date 2023/7/15
 */
public class ServiceHelper {

    private static final List<Class<?>> SERVICE_SPEC_INTERFACES;

    static {
        SERVICE_SPEC_INTERFACES = new ArrayList<>();
        SERVICE_SPEC_INTERFACES.add(Handler.class);
        SERVICE_SPEC_INTERFACES.add(CommandHandler.class);
        SERVICE_SPEC_INTERFACES.add(QueryHandler.class);
    }

    public static boolean isExpectedServiceHandler(Class<?> clazz) {
        if (SERVICE_SPEC_INTERFACES.contains(clazz)) {
            return false;
        }
        return Arrays.stream(clazz.getInterfaces())
                .anyMatch(SERVICE_SPEC_INTERFACES::contains);
    }

    public static Class<?> getRequestClass(Class<?> clazz) {
        String className = Arrays.stream(clazz.getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedTypeImpl)
                .map(type -> (ParameterizedTypeImpl) type)
                .filter(parameterizedType -> SERVICE_SPEC_INTERFACES.contains(parameterizedType.getRawType()))
                .map(parameterizedType -> parameterizedType.getActualTypeArguments()[1].getTypeName())
                .findFirst()
                .orElseThrow(() -> new DispatchException("There is no request class."));

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new DispatchException("Get handler's request class fail, class not found.");
        }
    }
}
