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

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class ClassUtils {

    public static Map<String, Object> toMap(Object object) {
        Objects.requireNonNull(object, "object must not null");
        List<Field> fieldList = FieldUtils.getAllFieldsList(object.getClass());
        Map<String, Object> map = new HashMap<>(fieldList.size());
        fieldList.forEach(field -> {
            try {
                map.put(field.getName(), FieldUtils.readField(field, object, true));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return map;
    }
}

