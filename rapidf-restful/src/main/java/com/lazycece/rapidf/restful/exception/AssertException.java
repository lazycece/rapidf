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

import com.lazycece.rapidf.restful.Status;

/**
 * @author lazycece
 * @date 2021/11/3
 */
public class AssertException extends AbstractCommonException {

    private final Status status;

    public AssertException(Status status) {
        this.status = status;
    }

    public AssertException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public AssertException(String message, Throwable cause, Status status) {
        super(message, cause);
        this.status = status;
    }

    public AssertException(Throwable cause, Status status) {
        super(cause);
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
