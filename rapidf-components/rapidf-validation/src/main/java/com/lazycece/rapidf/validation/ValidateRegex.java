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

package com.lazycece.rapidf.validation;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class ValidateRegex {

    /**
     * 验证用户名：包含大小写字母、数字、下划线，长度4-20位
     */
    public static final String USERNAME = "^[a-zA-Z0-9_]{4,20}$";

    /**
     * 验证密码：包含大小写字母、数字，长度6-20位
     */
    public static final String PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 验证手机号
     */
    public static final String TELEPHONE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 验证邮箱
     */
    public static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 验证汉字
     */
    public static final String CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 验证身份证
     */
    public static final String ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 验证URL
     */
    public static final String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 验证IP地址
     */
    public static final String IP = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
}
