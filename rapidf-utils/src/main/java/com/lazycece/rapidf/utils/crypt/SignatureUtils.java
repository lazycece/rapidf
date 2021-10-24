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

package com.lazycece.rapidf.utils.crypt;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * for sign param, and all param spilt by '&'
 * <p>
 * eg: param_one=value_one&param_two=value_tow&param_three=value_three
 *
 * @author lazycece
 */
public class SignatureUtils {

    public static String generate(final Map<String, String> map, String key, String... excludes) {
        return generate(map, key, Arrays.asList(excludes));
    }

    public static String generate(final Map<String, String> map, String key, List<String> excludes) {
        Set<String> keySet = map.keySet();
        String[] keyArray = keySet.toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (excludes.contains(k)) {
                continue;
            }
            String value = map.get(k);
            if (value != null && value.trim().length() > 0) {
                sb.append(k).append("=").append(map.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }
}
