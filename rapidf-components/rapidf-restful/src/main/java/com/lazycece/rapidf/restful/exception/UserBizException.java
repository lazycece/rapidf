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

package com.lazycece.rapidf.restful.exception;

import com.lazycece.rapidf.restful.response.RespStatus;
import com.lazycece.rapidf.restful.response.Status;

/**
 * @author lazycece
 * @date 2023/03/16
 */
public class UserBizException extends AbstractBaseException {

    public UserBizException() {
    }

    public UserBizException(String message) {
        super(message);
    }

    public UserBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBizException(Throwable cause) {
        super(cause);
    }

    @Override
    public Status getStatus() {
        return RespStatus.USER_BIZ_FAIL;
    }
}
