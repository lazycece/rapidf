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
    AUTH_TOKEN_FAIL(-100, "auth token fail", false),
    AUTH_SIGN_FAIL(-101, "auth sign fail", false),
    AUTH_PARAM_FAIL(-102, "auth param fail", false),
    SUCCESS(200, "success", false),
    ACCESS_DENIED(403, "access denied", false),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", false),
    INTEGRATION_ERROR(600, "integration error", false),
    FAIL(800, "fail", false),
    PARAM_ERROR(801, "param error", false);
    private final int code;
    private Family family;
    private final String message;
    private final boolean canRetry;

    RespStatus(int code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
        switch (code / 100) {
            case -1:
                this.family = Family.AUTH;
                break;
            case 2:
                this.family = Family.SUCCESS;
                break;
            case 4:
                this.family = Family.CLIENT_ERROR;
                break;
            case 5:
                this.family = Family.SERVER_ERROR;
                break;
            case 6:
                this.family = Family.INTEGRATION_ERROR;
                break;
            case 8:
                this.family = Family.FAIL;
                break;
            default:
        }
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Family getFamily() {
        return family;
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