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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author lazycece
 * @date 2021/10/31
 */
public class DefaultUtils {

    public static String defaultValue(final String value, final String defaultValue) {
        return StringUtils.isNotBlank(value) ? value : defaultValue;
    }

    public static <T> List<T> defaultList(final List<T> list) {
        return list != null ? list : new ArrayList<>();
    }

    public static <K, V> Map<K, V> defaultMap(final Map<K, V> map) {
        return map != null ? map : new HashMap<>();
    }

    /**
     * Get default value if object is null , otherwise ${@link Function#apply}
     *
     * @param obj          origin object
     * @param function     custom function for acquiring value
     * @param defaultValue default value
     * @param <T>          obj type
     * @param <V>          value type
     * @return value
     */
    public static <T, V> V defaultValueIfNullObj(final T obj, final Function<T, V> function,
                                                 final V defaultValue) {
        return obj != null ? function.apply(obj) : defaultValue;
    }
}
