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
import com.lazycece.rapidf.rapidf.example.constants.LogTypeConstants;
import com.lazycece.rapidf.rapidf.example.req.ValidateEnumReq;
import com.lazycece.rapidf.restful.response.RespMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lazycece
 * @date 2021/11/16
 */
@RestController
public class ExampleController {

    @GetMapping("/hello")
    @Logger(symbol = "hello",
            digestType = LogTypeConstants.CONTROLLER_DIGEST,
            detailType = LogTypeConstants.CONTROLLER_DETAIL,
            blacklist = {int.class})
    // TODO: 2021/11/16 接口注解？ 非接口注解? rpc子类实现注解？
    public RespMap example(String name, int age) {
        String data = String.format("hello, %s,  %s years old.", name, age);
        return RespMap.success(data);
    }

    @PostMapping("/validateEnum")
    public RespMap validateEnum(@Validated ValidateEnumReq req) {
        String data = String.format("tag = %s, status = %s", req.getTag(), req.getStatus());
        return RespMap.success(data);
    }
}
