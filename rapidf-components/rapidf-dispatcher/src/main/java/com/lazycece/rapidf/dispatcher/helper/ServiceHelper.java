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
import java.util.Optional;

/**
 * @author lazycece
 * @date 2023/7/15
 */
public class ServiceHelper {

    private static final List<String> SERVICE_SPEC_INTERFACES;

    static {
        SERVICE_SPEC_INTERFACES = new ArrayList<>();
        SERVICE_SPEC_INTERFACES.add(Handler.class.getName());
        SERVICE_SPEC_INTERFACES.add(CommandHandler.class.getName());
        SERVICE_SPEC_INTERFACES.add(QueryHandler.class.getName());
    }

    public static boolean isExpectedServiceHandler(Class<?> clazz) {
        return Arrays.stream(clazz.getInterfaces())
                .anyMatch(cls -> SERVICE_SPEC_INTERFACES.contains(cls.getName()));
    }

    public static Class<?> getRequestClass(Class<?> clazz) {
        Optional<String> optional = Arrays.stream(clazz.getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedTypeImpl)
                .map(type -> (ParameterizedTypeImpl) type)
                .filter(parameterizedType -> SERVICE_SPEC_INTERFACES.contains(parameterizedType.getRawType().getName()))
                .map(parameterizedType -> parameterizedType.getActualTypeArguments()[1].getTypeName())
                .findFirst();

        if (!optional.isPresent()) {
            throw new DispatchException("There is no request class.");
        }
        try {
            return Class.forName(optional.get());
        } catch (ClassNotFoundException e) {
            throw new DispatchException("Get handler's request class fail, class not found.");
        }
    }
}
