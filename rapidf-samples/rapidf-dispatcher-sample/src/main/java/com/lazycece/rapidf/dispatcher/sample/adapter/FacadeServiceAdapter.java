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

package com.lazycece.rapidf.dispatcher.sample.adapter;

import com.lazycece.rapidf.dispatcher.core.Dispatcher;
import com.lazycece.rapidf.dispatcher.core.facade.FacadeCmd;
import com.lazycece.rapidf.restful.response.RespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lazycece
 * @date 2023/7/17
 */
@RestController
@RequestMapping(value = "/facade")
public class FacadeServiceAdapter {

    @Autowired
    private Dispatcher dispatcher;

    @PostMapping("/handle")
    public RespData<?> handle(@RequestBody @Validated FacadeRequest request) {
        FacadeCmd cmd = new FacadeCmd();
        cmd.setName(request.getName());
        cmd.setVersion(request.getVersion());
        cmd.setAction(request.getAction());
        cmd.setRequest(request.getRequestData());
        return RespData.success(dispatcher.dispatch(cmd));
    }
}
