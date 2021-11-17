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

package com.lazycece.rapidf.rapidf.example.controller;

import com.lazycece.rapidf.logger.annotation.Logger;
import com.lazycece.rapidf.rapidf.example.constants.LogNameConstants;
import com.lazycece.rapidf.rapidf.example.service.HelloService;
import com.lazycece.rapidf.restful.exception.CommonException;
import com.lazycece.rapidf.restful.response.RespMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lazycece
 * @date 2021/11/17
 */
@RestController
public class LogController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/logHello")
    @Logger(symbol = "log-hello",
            digestType = LogNameConstants.CONTROLLER_DIGEST,
            detailType = LogNameConstants.CONTROLLER_DETAIL)
    public RespMap logHello(String name) {
        return RespMap.success(helloService.hello(name));
    }

    @GetMapping("/logBlacklist")
    @Logger(symbol = "log-blacklist",
            digestType = LogNameConstants.CONTROLLER_DIGEST,
            detailType = LogNameConstants.CONTROLLER_DETAIL,
            blacklist = {Integer.class})
    public RespMap logBlacklist(String arg1, int arg2) {
        String data = String.format("Log parameter: %s, %s", arg1, arg2);
        return RespMap.success(data);
    }

    @GetMapping("/logEmptyResult")
    @Logger(symbol = "log-empty-result",
            digestType = LogNameConstants.CONTROLLER_DIGEST,
            detailType = LogNameConstants.CONTROLLER_DETAIL)
    public void logEmptyResult() {
    }

    @GetMapping("/logThrow")
    @Logger(symbol = "log-throw",
            digestType = LogNameConstants.CONTROLLER_DIGEST,
            detailType = LogNameConstants.CONTROLLER_DETAIL)
    public void logThrow() {
        throw new CommonException("logThrow");
    }

    @GetMapping("/logNo")
    public void logNo() {
    }
}
