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
import com.lazycece.rapidf.restful.response.RespData;
import com.lazycece.rapidf.restful.response.RespStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom exception handle with ${@link RespData}
 *
 * @author lazycece
 * @date 2019/02/23
 */
public class RespDataExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RespDataExceptionHandler.class);

    /**
     * Bind exception handle.
     *
     * @param e ${@link BindException}
     * @return see ${@link RespData}
     */
    @ExceptionHandler(value = BindException.class)
    public RespData<?> bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessage = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return RespData.fail(RespStatus.PARAM_ERROR.getCode(), errorMessage.toString());
    }

    /**
     * validation exception handle.
     *
     * @param e ${@link ValidationException}
     * @return see ${@link RespData}
     */
    @ExceptionHandler(value = ValidationException.class)
    public RespData<?> validationExceptionHandler(ValidationException e) {
        return RespData.fail(RespStatus.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * Custom exception handle.
     * <p>we need to cut off message from "|",see ${@link AbstractBaseException#getMessage()}</p>
     *
     * @param e ${@link AbstractBaseException}
     * @return see ${@link RespData}
     */
    @ExceptionHandler(value = AbstractBaseException.class)
    public RespData<?> customExceptionHandler(AbstractBaseException e) {
        String message = e.getMessage();
        int cut = message.indexOf("|");
        message = message.substring(cut + 1);
        return RespData.fail(e.getStatus().getCode(), message);
    }

    /**
     * Unexpected exception handle.
     *
     * @param e ${@link Exception}
     * @return see ${@link RespData}
     */
    @ExceptionHandler(value = Exception.class)
    public RespData<?> unexpectedExceptionHandler(Exception e) {
        log.error("{}:", RespStatus.INTERNAL_SERVER_ERROR.getMessage(), e);
        return RespData.status(RespStatus.INTERNAL_SERVER_ERROR);
    }
}
