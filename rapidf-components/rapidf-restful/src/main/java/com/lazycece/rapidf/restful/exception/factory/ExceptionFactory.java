/*
 *    Copyright 2023 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.restful.exception.factory;

import com.lazycece.rapidf.restful.exception.*;
import com.lazycece.rapidf.restful.response.Status;

/**
 * @author lazycece
 * @date 2022/12/11
 */
public class ExceptionFactory {

    public static AuthException authException(String message) {
        return new AuthException(message);
    }

    public static AuthException authException(String message, Throwable cause) {
        return new AuthException(message, cause);
    }

    public static ClientException clientException(String message) {
        return new ClientException(message);
    }

    public static ClientException clientException(String message, Throwable cause) {
        return new ClientException(message, cause);
    }

    public static UserBizException userBizException(String message) {
        return new UserBizException(message);
    }

    public static UserBizException userBizException(String message, Throwable cause) {
        return new UserBizException(message, cause);
    }

    public static UserBizException userBizException(Throwable cause) {
        return new UserBizException(cause);
    }

    public static ParamException paramException(String message) {
        return new ParamException(message);
    }

    public static ParamException paramException(String message, Throwable cause) {
        return new ParamException(message, cause);
    }

    public static ParamException paramException(Throwable cause) {
        return new ParamException(cause);
    }

    public static BusinessException businessException(String message) {
        return new BusinessException(message);
    }

    public static BusinessException businessException(String message, Throwable cause) {
        return new BusinessException(message, cause);
    }

    public static BusinessException businessException(Throwable cause) {
        return new BusinessException(cause);
    }

    public static ServerException serverException(String message) {
        return new ServerException(message);
    }

    public static ServerException serverException(String message, Throwable cause) {
        return new ServerException(message, cause);
    }

    public static ServerException serverException(Throwable cause) {
        return new ServerException(cause);
    }

    public static IntegrationException integrationException(String message) {
        return new IntegrationException(message);
    }

    public static IntegrationException integrationException(String message, Throwable cause) {
        return new IntegrationException(message, cause);
    }

    public static IntegrationException integrationException(Throwable cause) {
        return new IntegrationException(cause);
    }

    public static AssertException assertException(String message, Status status) {
        return new AssertException(message, status);
    }

    public static AssertException assertException(String message, Throwable cause, Status status) {
        return new AssertException(message, cause, status);
    }

    public static AssertException assertException(Throwable cause, Status status) {
        return new AssertException(cause, status);
    }

    public static CommonException commonException(String message) {
        return new CommonException(message);
    }

    public static CommonException commonException(String message, Status status) {
        return new CommonException(message, status);
    }

    public static CommonException commonException(String message, Throwable cause, Status status) {
        return new CommonException(message, cause, status);
    }

    public static CommonException commonException(Throwable cause, Status status) {
        return new CommonException(cause, status);
    }
}
