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

package com.lazycece.rapidf.logger.interceptor;

import com.lazycece.rapidf.logger.LogInfo;
import com.lazycece.rapidf.logger.annotation.Logger;
import com.lazycece.rapidf.logger.parser.DefaultLogParser;
import com.lazycece.rapidf.logger.parser.LogParser;
import com.lazycece.rapidf.logger.utils.LogUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author lazycece
 * @date 2021/11/6
 */
public class LogInterceptor implements MethodInterceptor {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    /**
     * Log parser, see ${@link LogParser}, you can customize it.
     */
    private LogParser logParser = new DefaultLogParser();

    /**
     * Log's information class, you can customize it,
     * but you must inherit ${@link LogInfo}
     */
    private Class<? extends LogInfo> logInfoClass = LogInfo.class;

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {

        if (!invocation.getMethod().isAnnotationPresent(Logger.class)) {
            return invocation.proceed();
        }

        // annotation logger method, print log.
        Logger logger = invocation.getMethod().getAnnotation(Logger.class);

        LogInfo logInfo = logInfoClass.newInstance();
        before(logInfo);

        Object result = null;
        boolean exceptional = false;
        try {
            result = invocation.proceed();
        } catch (Throwable throwable) {
            exceptional = true;
            throw throwable;
        } finally {
            try {
                after(logInfo, logger, invocation, result, exceptional);
            } catch (Throwable throwable) {
                LogUtils.error(LOGGER, "logger processing error.", throwable);
            }
        }
        return result;
    }

    private void before(LogInfo logInfo) {
        logInfo.setEnterTime(System.currentTimeMillis());
    }

    private void after(LogInfo logInfo, Logger logger, MethodInvocation invocation,
                       Object result, boolean exceptional) {
        logInfo.setOutTime(System.currentTimeMillis());
        logInfo.setResult(result);

        boolean success = !exceptional && logParser.isSuccess(result);
        String code = logParser.getCode(result);
        logInfo.setSuccess(success);
        logInfo.setCode(code);

        logInfo.setClassName(invocation.getMethod().getDeclaringClass().getSimpleName());
        logInfo.setMethodName(invocation.getMethod().getName());
        logInfo.setArgs(invocation.getArguments());

        logInfo.setSymbol(logger.symbol());
        logInfo.setBlacklist(logger.blacklist());

        Class<?> defaultLogClass = invocation.getMethod().getDeclaringClass();

        if (StringUtils.isNotBlank(logger.digestLogName())) {
            LogUtils.info(LoggerFactory.getLogger(logger.digestLogName()),
                    logInfo.digestLog());
        } else {
            LogUtils.info(LoggerFactory.getLogger(defaultLogClass),
                    logInfo.digestLog());
        }

        if (StringUtils.isNotBlank(logger.detailLogName())) {
            LogUtils.info(LoggerFactory.getLogger(logger.detailLogName()),
                    logInfo.detailLog());
        } else {
            LogUtils.info(LoggerFactory.getLogger(defaultLogClass),
                    logInfo.detailLog());
        }
    }

    public LogParser getLogParser() {
        return logParser;
    }

    public void setLogParser(LogParser logParser) {
        this.logParser = logParser;
    }

    public Class<? extends LogInfo> getLogInfoClass() {
        return logInfoClass;
    }

    public void setLogInfoClass(Class<? extends LogInfo> logInfoClass) {
        this.logInfoClass = logInfoClass;
    }
}
