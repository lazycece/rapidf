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

package com.lazycece.rapidf.logger.utils;

import org.slf4j.Logger;

/**
 * @author lazycece
 * @date 2021/11/6
 */
public class LogUtils {

    public static void debug(Logger logger, String message) {
        if (logger == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public static void debug(Logger logger, String format, Object... args) {
        if (logger == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(format, args);
        }
    }

    public static void debug(Logger logger, String message, Throwable throwable) {
        if (logger == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(message, throwable);
        }
    }

    public static void info(Logger logger, String message) {
        if (logger == null) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public static void info(Logger logger, String format, Object... args) {
        if (logger == null) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info(format, args);
        }
    }

    public static void info(Logger logger, String message, Throwable throwable) {
        if (logger == null) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info(message, throwable);
        }
    }

    public static void warn(Logger logger, String message) {
        if (logger == null) {
            return;
        }
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    public static void warn(Logger logger, String format, Object... args) {
        if (logger == null) {
            return;
        }
        if (logger.isWarnEnabled()) {
            logger.warn(format, args);
        }
    }

    public static void warn(Logger logger, String message, Throwable throwable) {
        if (logger == null) {
            return;
        }
        if (logger.isWarnEnabled()) {
            logger.warn(message, throwable);
        }
    }

    public static void error(Logger logger, String message) {
        if (logger == null) {
            return;
        }
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }

    public static void error(Logger logger, String format, Object... args) {
        if (logger == null) {
            return;
        }
        if (logger.isErrorEnabled()) {
            logger.error(format, args);
        }
    }

    public static void error(Logger logger, String message, Throwable throwable) {
        if (logger == null) {
            return;
        }
        if (logger.isErrorEnabled()) {
            logger.error(message, throwable);
        }
    }

    public static void trace(Logger logger, String message) {
        if (logger == null) {
            return;
        }
        if (logger.isTraceEnabled()) {
            logger.trace(message);
        }
    }

    public static void trace(Logger logger, String format, Object... args) {
        if (logger == null) {
            return;
        }
        if (logger.isTraceEnabled()) {
            logger.trace(format, args);
        }
    }

    public static void trace(Logger logger, String message, Throwable throwable) {
        if (logger == null) {
            return;
        }
        if (logger.isTraceEnabled()) {
            logger.trace(message, throwable);
        }
    }
}
