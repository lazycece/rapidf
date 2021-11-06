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

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class RespData<T> {

    private Integer code;
    private String message;
    private T body;

    public RespData() {
    }

    private RespData(Integer code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    private RespData(Status status) {
        this(status.getCode(), status.getMessage(), null);
    }

    private RespData(RespStatus status, T body) {
        this(status.getCode(), status.getMessage(), body);
    }

    public static RespData<?> success() {
        return new RespData<>(RespStatus.SUCCESS);
    }

    public static <T> RespData<T> success(T body) {
        return new RespData<>(RespStatus.SUCCESS, body);
    }

    public static RespData<?> fail() {
        return new RespData<>(RespStatus.FAIL);
    }

    public static RespData<?> fail(String message) {
        return new RespData<>(RespStatus.FAIL.getCode(), message, null);
    }

    public static RespData<?> fail(Integer code, String message) {
        return new RespData<>(code, message, null);
    }

    public static RespData<?> status(Status status) {
        return new RespData<>(status);
    }

    public boolean isSuccess() {
        return this.code != null && this.code == RespStatus.SUCCESS.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
