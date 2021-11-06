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

package com.lazycece.rapidf.restful.response;

import java.util.HashMap;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class RespMap extends HashMap<String, Object> {

    private static final String CODE_FIELD = "code";
    private static final String MESSAGE_FIELD = "message";
    private static final String BODY_FIELD = "body";

    public RespMap() {
    }

    private RespMap(Integer code, String message) {
        this.put(CODE_FIELD, code);
        this.put(MESSAGE_FIELD, message);
    }

    private RespMap(Status status) {
        this(status.getCode(), status.getMessage());
    }

    public static RespMap success() {
        return new RespMap(RespStatus.SUCCESS);
    }

    public static RespMap success(Object body) {
        return success().putting(BODY_FIELD, body);
    }

    public static RespMap fail() {
        return new RespMap(RespStatus.FAIL);
    }

    public static RespMap fail(String message) {
        return new RespMap(RespStatus.FAIL.getCode(), message);
    }

    public static RespMap fail(Integer code, String message) {
        return new RespMap(code, message);
    }

    public static RespMap status(Status status) {
        return new RespMap(status);
    }

    public RespMap putting(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public boolean isSuccess() {
        return RespStatus.SUCCESS.getCode() == (int) this.getOrDefault(
                CODE_FIELD, RespStatus.FAIL.getCode());
    }

    public Integer getCode() {
        return (int) this.get(CODE_FIELD);
    }

    public void setCode(Integer code) {
        this.put(CODE_FIELD, code);
    }

    public String getMessage() {
        return String.valueOf(this.get(MESSAGE_FIELD));
    }

    public void setMessage(String message) {
        this.put(MESSAGE_FIELD, message);
    }

    public Object getBody() {
        return this.get(BODY_FIELD);
    }

    public void setBody(Object body) {
        this.put(BODY_FIELD, body);
    }
}

