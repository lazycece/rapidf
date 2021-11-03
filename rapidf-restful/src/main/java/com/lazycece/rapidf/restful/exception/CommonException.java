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

import com.lazycece.rapidf.restful.RespStatus;
import com.lazycece.rapidf.restful.Status;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class CommonException extends AbstractCommonException {

    private Status status = RespStatus.FAIL;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message, Throwable cause, Status status) {
        super(message, cause);
        this.status = status;
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(Throwable cause, Status status) {
        super(cause);
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}

