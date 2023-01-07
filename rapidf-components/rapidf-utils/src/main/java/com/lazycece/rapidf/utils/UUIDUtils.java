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

import com.lazycece.rapidf.utils.constants.SymbolConstants;

import java.math.BigInteger;
import java.util.UUID;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class UUIDUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll(
                SymbolConstants.HYPHEN, SymbolConstants.EMPTY);
    }

    public static String sn(int num) {
        String uuid = uuid();
        String sn = new BigInteger(uuid, 16).toString();
        if (num > sn.length()) {
            throw new RuntimeException(String.format("num must less and equal %s", sn.length()));
        }
        return sn.substring(0, num);
    }
}
