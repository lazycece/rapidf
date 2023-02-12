/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.utils;

import java.lang.reflect.Method;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class EnumUtils {

    private static final String ENUM_METHOD_GET_VALUE = "getCode";

    public static <T> T getEnum(Class<T> clazz, Object value) {
        return getEnum(clazz, ENUM_METHOD_GET_VALUE, value);
    }

    public static <T> T getEnum(Class<T> clazz, String enumMethod, Object value) {

        try {
            Method method = clazz.getMethod(enumMethod);
            for (T t : clazz.getEnumConstants()) {
                if (value.equals(method.invoke(t))) {
                    return t;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}

