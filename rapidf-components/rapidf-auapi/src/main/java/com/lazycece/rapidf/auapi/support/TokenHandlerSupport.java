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

package com.lazycece.rapidf.auapi.support;

import com.lazycece.au.api.params.utils.JsonUtils;
import com.lazycece.au.api.token.TokenHandler;
import com.lazycece.rapidf.restful.response.RespData;
import com.lazycece.rapidf.restful.response.RespStatus;
import org.springframework.stereotype.Component;

/**
 * @author lazycece
 * @date 2023/5/15
 */
@Component
public class TokenHandlerSupport implements TokenHandler {

    @Override
    public String noToken() {
        return JsonUtils.toJSONString(RespData.fail(RespStatus.AUTH_TOKEN_FAIL.getCode(), "token is null"));
    }

    @Override
    public String invalidToken() {
        return JsonUtils.toJSONString(RespData.fail(RespStatus.AUTH_TOKEN_FAIL.getCode(), "invalid token"));
    }
}
