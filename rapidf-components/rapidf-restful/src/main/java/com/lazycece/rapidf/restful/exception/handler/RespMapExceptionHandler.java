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

package com.lazycece.rapidf.restful.exception.handler;

import com.lazycece.rapidf.restful.exception.AbstractBaseException;
import com.lazycece.rapidf.restful.response.RespMap;
import com.lazycece.rapidf.restful.response.RespStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Custom exception handle with ${@link RespMap}
 *
 * @author lazycece
 * @date 2019/02/23
 */
public class RespMapExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RespMapExceptionHandler.class);

    /**
     * Bind exception handle.
     *
     * @param e ${@link BindException}
     * @return see ${@link RespMap}
     */
    @ExceptionHandler(value = BindException.class)
    public RespMap bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getAllErrors().forEach(
                objectError ->
                        stringBuilder.append(",").append(objectError.getDefaultMessage())
        );
        String errorMessage = stringBuilder.toString();
        errorMessage = errorMessage.substring(1);
        return RespMap.fail(RespStatus.PARAM_ERROR.getCode(), errorMessage);
    }

    /**
     * Custom exception handle.
     * <p>we need to cut off message from "|",see ${@link AbstractBaseException#getMessage()}</p>
     *
     * @param e ${@link AbstractBaseException}
     * @return see ${@link RespMap}
     */
    @ExceptionHandler(value = AbstractBaseException.class)
    public RespMap customExceptionHandler(AbstractBaseException e) {
        String message = e.getMessage();
        int cut = message.indexOf("|");
        message = message.substring(cut + 1);
        return RespMap.fail(e.getStatus().getCode(), message);
    }

    /**
     * Unexpected exception handle.
     *
     * @param e ${@link Exception}
     * @return see ${@link RespMap}
     */
    @ExceptionHandler(value = Exception.class)
    public RespMap unexpectedExceptionHandler(Exception e) {
        log.error("{}:", RespStatus.INTERNAL_SERVER_ERROR.getMessage(), e);
        return RespMap.status(RespStatus.INTERNAL_SERVER_ERROR);
    }
}
