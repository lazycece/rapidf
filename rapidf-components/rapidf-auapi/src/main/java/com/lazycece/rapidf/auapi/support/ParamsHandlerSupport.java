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

import com.alibaba.fastjson.JSONObject;
import com.lazycece.au.api.params.ParamsHandler;
import com.lazycece.au.api.params.utils.JsonUtils;
import com.lazycece.rapidf.auapi.constants.ApiConstants;
import com.lazycece.rapidf.restful.response.RespData;
import com.lazycece.rapidf.restful.response.RespStatus;
import org.springframework.stereotype.Component;

/**
 * @author lazycece
 * @date 2023/5/15
 */
@Component
public class ParamsHandlerSupport implements ParamsHandler {

    @Override
    public String validateParamsFail() {
        return JsonUtils.toJSONString(RespData.fail(RespStatus.AUTH_PARAM_FAIL.getCode(), "validate param fail"));
    }

    @Override
    public String validateTimeFail() {
        return JsonUtils.toJSONString(RespData.fail(RespStatus.AUTH_PARAM_FAIL.getCode(), "invalid request"));
    }

    @Override
    public String validateSignFail() {
        return JsonUtils.toJSONString(RespData.fail(RespStatus.AUTH_SIGN_FAIL.getCode(), "validate sign fail"));
    }

    @Override
    public String getWaitEncodeData(String responseBody) {
        RespData<?> respData = JsonUtils.parseObject(responseBody, RespData.class);
        return JsonUtils.toJSONString(respData.getBody());
    }

    @Override
    public String getResponseBody(String responseBody, String encodeData, String salt) {
        JSONObject response = JsonUtils.parseObject(responseBody, JSONObject.class);
        response.put(ApiConstants.RESPONSE_FIELD_BODY, encodeData);
        response.put(ApiConstants.RESPONSE_FIELD_SALT, salt);
        return JsonUtils.toJSONString(response);
    }
}
