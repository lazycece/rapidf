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

import com.lazycece.rapidf.utils.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class ConvertUtils {

    public static List<String> formatList(Object obj) {
        return JsonUtils.parseArray(formatString(obj), String.class);
    }

    public static List<Integer> formatList2Int(Object obj) {
        return JsonUtils.parseArray(formatString(obj), Integer.class);
    }

    public static Integer formatInteger(Object obj) {
        try {
            return Integer.valueOf(Objects.requireNonNull(formatString(obj)));
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer formatInteger(Object obj, Integer d) {
        try {
            String o = formatString(obj);
            if (StringUtils.isBlank(o)) {
                return d;
            }
            return Integer.valueOf(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long formatLong(Object obj) {
        try {
            String value = formatString(obj);
            if (value == null) {
                return null;
            }
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double formatDouble(Object obj) {
        try {
            String value = formatString(obj);
            if (value == null) {
                return null;
            }
            return Double.valueOf(value);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Float formatFloat(Object obj) {
        try {
            String value = formatString(obj);
            if (value == null) {
                return null;
            }
            return Float.valueOf(value);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String formatString(Object obj) {
        try {
            if (obj == null) {
                return null;
            }
            return String.valueOf(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean formatBoolean(Object obj) {
        try {
            if (obj == null) {
                return false;
            }
            return Boolean.parseBoolean(String.valueOf(obj));
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatString(Object obj, String d) {
        try {
            if (obj == null || StringUtils.isBlank(String.valueOf(obj))) {
                return d;
            }
            return String.valueOf(obj);
        } catch (Exception e) {
            return null;
        }
    }
}

