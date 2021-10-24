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

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class UUIDUtils {

    /**
     * validate a string is blank or not.
     * return <code>true</code>, if string is null，"" or  "'   '";
     * otherwise return <code>false</code>
     *
     * @param str string
     * @return true/false
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * validate a string isn't blank or be.
     * return <code>false</code>, if string is null，"" or  "'   '";
     * otherwise return <code>true</code>
     *
     * @param str string
     * @return true/false
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
