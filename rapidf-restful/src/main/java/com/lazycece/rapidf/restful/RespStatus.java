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

package com.lazycece.rapidf.restful;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public enum RespStatus implements Status {

    AUTH_TOKEN_FAIL(100, "Auth token fail", false),
    AUTH_SIGN_FAIL(101, "Auth sign fail", false),
    AUTH_PARAM_FAIL(102, "Auth param fail", false),
    SUCCESS(200, "Success", false),
    CLIENT_ERROR(400, "Client Error", false),
    ACCESS_DENIED(403, "Access Denied", false),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", false),
    DB_EXCEPTION(501, "DB Exception ", false),
    INTEGRATION_ERROR(600, "Integration Error", false),
    FAIL(700, "Fail", false),
    PARAM_ERROR(701, "Param Error", false),
    DATA_NOT_EXIST(702, "Data Not Exist", false);

    private final int code;
    private final Family family;
    private final String message;
    private final boolean canRetry;

    RespStatus(int code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
        this.family = getFamily(code);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Family getFamily() {
        return family;
    }

    public Family getFamily(int code) {
        switch (code / 100) {
            case 1:
                return Family.AUTH;
            case 2:
                return Family.SUCCESS;
            case 4:
                return Family.CLIENT;
            case 5:
                return Family.SERVER;
            case 6:
                return Family.INTEGRATION;
            case 7:
            case 8:
            case 9:
                return Family.FAIL;
            default:
                return null;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isCanRetry() {
        return canRetry;
    }

    @Override
    public String toString() {
        return String.format("%s[%s,%s,%s]|%s",
                this.name(), code, family, canRetry, message);
    }
}