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

package com.lazycece.rapidf.logger;

import com.lazycece.rapidf.utils.DefaultUtils;

import static com.lazycece.rapidf.utils.constants.CommonConstants.*;
import static com.lazycece.rapidf.utils.constants.SymbolConstants.*;

/**
 * @author lazycece
 * @date 2021/11/6
 */
public class LogInfo implements Log {

    private static final String REQUEST = "REQUEST";
    private static final String RESULT = "RESULT";

    private String symbol;
    private boolean success;
    private String code;
    private Object result;
    private String className;
    private String methodName;
    private long enterTime;
    private long outTime;
    private Object[] args;
    private Class<?>[] blacklist;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Class<?>[] blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public String digestLog() {

        // Format: [(symbol)(className.methodName,success,consumeTime)(code)]

        StringBuilder sb = new StringBuilder();

        // [
        sb.append(L_BRACKETS);

        // (symbol)
        sb
                .append(L_PARENTHESES)
                .append(DefaultUtils.defaultValue(symbol, HYPHEN))
                .append(R_PARENTHESES);

        // (className.methodName,success,consumeTime)
        sb
                .append(L_PARENTHESES)
                .append(DefaultUtils.defaultValue(className, HYPHEN))
                .append(DOT)
                .append(DefaultUtils.defaultValue(methodName, HYPHEN))
                .append(COMMA)
                .append(success ? SUCCESS : FAIL)
                .append(COMMA)
                .append(outTime - enterTime).append(MS)
                .append(R_PARENTHESES);

        // (code)
        sb
                .append(L_PARENTHESES)
                .append(DefaultUtils.defaultValue(code, HYPHEN))
                .append(R_PARENTHESES);
        // ]

        sb.append(R_BRACKETS);

        return sb.toString();
    }

    @Override
    public String detailLog() {

        // Format: [symbol,className.methodName][REQUEST(arg1,arg2)][RESULT(result)]

        StringBuilder sb = new StringBuilder();

        // [symbol,className.methodName]
        sb
                .append(L_BRACKETS)
                .append(DefaultUtils.defaultValue(symbol, HYPHEN))
                .append(COMMA)
                .append(DefaultUtils.defaultValue(className, HYPHEN))
                .append(DOT)
                .append(DefaultUtils.defaultValue(methodName, HYPHEN))
                .append(R_BRACKETS);

        // [REQUEST(arg1,arg2)]
        sb.append(L_BRACKETS).append(REQUEST).append(L_PARENTHESES);
        for (Object object : DefaultUtils.defaultArray(args)) {
            if (object == null || inBlacklist(object.getClass())) {
                continue;
            }
            sb.append(object).append(COMMA);
        }
        sb.append(R_PARENTHESES).append(R_BRACKETS);

        // [RESULT(result)]
        sb.append(L_BRACKETS).append(RESULT).append(L_PARENTHESES)
                .append(result)
                .append(R_PARENTHESES).append(R_BRACKETS);

        return sb.toString();
    }

    private boolean inBlacklist(Class<?> clazz) {
        for (Class<?> clz : DefaultUtils.defaultArray(blacklist)) {
            if (clz.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }
}
