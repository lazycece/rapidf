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

package com.lazycece.rapidf.restful.sample.controller;

import com.lazycece.rapidf.restful.response.RespData;
import com.lazycece.rapidf.restful.response.RespMap;
import com.lazycece.rapidf.restful.sample.request.HelloRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lazycece
 * @date 2021/11/16
 */
@RestController
public class RestfulController {

    @GetMapping("/hello")
    public RespMap example(String name, int age) {
        String data = String.format("hello, %s,  %s years old.", name, age);
        return RespMap.success(data);
    }

    @GetMapping("/hello/exception1")
    public RespData<?> exception1(@RequestParam String name, int age) {
        String data = String.format("hello, %s,  %s years old.", name, age);
        return RespData.success(data);
    }

    @GetMapping("/hello/exception2")
    public RespData<?> exception2(@Validated HelloRequest request) {
        String data = String.format("hello, %s,  %s years old.", request.getName(), request.getAge());
        return RespData.success(data);
    }

    @PostMapping("/hello/exceptionJson")
    public RespData<?> exceptionJson(@Validated @RequestBody HelloRequest request) {
        String data = String.format("hello, %s,  %s years old.", request.getName(), request.getAge());
        return RespData.success(data);
    }

    @GetMapping("/hello/exception3")
    public RespMap exception3(@RequestParam String name, int age) {
        String data = String.format("hello, %s,  %s years old.", name, age);
        return RespMap.success(data);
    }

    @GetMapping("/hello/exception4")
    public RespMap exception4(@Validated HelloRequest request) {
        String data = String.format("hello, %s,  %s years old.", request.getName(), request.getAge());
        return RespMap.success(data);
    }
}
