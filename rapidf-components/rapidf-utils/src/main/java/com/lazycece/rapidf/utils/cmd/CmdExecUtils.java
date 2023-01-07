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

package com.lazycece.rapidf.utils.cmd;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lazycece
 * @date 2020/2/6
 */
public class CmdExecUtils {

    /**
     * exec cmd will be blocked until the subprocess exits.
     *
     * @param cmd cmd cmd
     * @return exec success or not
     * @throws IOException          IOException
     * @throws InterruptedException Interrupted Exception
     */
    public static boolean execSuc(String cmd) throws IOException, InterruptedException {
        Process process = null;
        try {
            process = exec(cmd);
            int value = process.exitValue();
            process.destroy();
            return value == 0;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * exec cmd will be blocked until the subprocess exits.
     *
     * @param cmd cmd
     * @return Process
     * @throws IOException          IOException
     * @throws InterruptedException Interrupted Exception
     */
    public static Process exec(String cmd) throws IOException, InterruptedException {
        return exec(cmd, -1, null);
    }

    /**
     * exec cmd
     *
     * @param cmd     cmd
     * @param timeout timeout value, <code>timeout=-1</code> indicates it will block
     * @param unit    ${@link TimeUnit} <code>unit==null</code> indicates it will block
     * @return exec success or not
     * @throws IOException          IOException
     * @throws InterruptedException Interrupted Exception
     */
    public static boolean execSuc(String cmd, long timeout, TimeUnit unit) throws IOException, InterruptedException {
        Process process = null;
        try {
            process = exec(cmd, timeout, unit);
            int value = process.exitValue();
            process.destroy();
            return value == 0;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * exec cmd
     *
     * @param cmd     cmd
     * @param timeout timeout value, <code>timeout=-1</code> indicates it will block
     * @param unit    ${@link TimeUnit} <code>unit==null</code> indicates it will block
     * @return Process
     * @throws IOException          IOException
     * @throws InterruptedException Interrupted Exception
     */
    public static Process exec(String cmd, long timeout, TimeUnit unit) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(cmd);
        if (timeout != -1L && unit != null) {
            if (!process.waitFor(timeout, unit)) {
                process.destroy();
                throw new RuntimeException("exec cmd timeout, cmd =" + cmd);
            }
        } else {
            process.waitFor();
        }
        return process;
    }
}

