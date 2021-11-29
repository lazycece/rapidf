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

package com.lazycece.rapidf.utils.ffmp;

import com.lazycece.rapidf.utils.cmd.CmdExecUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author lazycece
 * @date 2020/2/6
 */
public class AudioInfoUtils {

    private static final AudioInfoUtils AUDIO_INFO_UTILS = new AudioInfoUtils();
    private static final String CMD_PARAMS = " -v quiet -print_format json -show_format -show_streams ";
    private static String FFPROBE_CMD;

    public static AudioInfoUtils getInstance(String ffprobePath) {
        FFPROBE_CMD = ffprobePath;
        return AUDIO_INFO_UTILS;
    }

    /**
     * Get audio's information, it will be blocked
     *
     * @param file audio file path
     * @return audio's information as json
     * @throws IOException          throw an IOException
     * @throws InterruptedException throw an InterruptedException
     */
    public String getAudioInfo(String file) throws IOException, InterruptedException {
        String cmd = FFPROBE_CMD + CMD_PARAMS + file;
        return execResult(cmd, -1, null);
    }

    /**
     * Get audio's information
     *
     * @param file    audio file path
     * @param timeout timeout
     * @param unit    unit
     * @return audio's information as json
     * @throws IOException          throw an IOException
     * @throws InterruptedException throw an InterruptedException
     */
    public String getAudioInfo(String file, long timeout, TimeUnit unit) throws IOException, InterruptedException {
        String cmd = FFPROBE_CMD + CMD_PARAMS + file;
        return execResult(cmd, timeout, unit);
    }

    private String execResult(String cmd, long timeout, TimeUnit unit) throws IOException, InterruptedException {
        Process process = null;
        try {
            process = CmdExecUtils.exec(cmd, timeout, unit);
            if (0 != process.exitValue()) {
                String error = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
                throw new RuntimeException("get audio information fail: " + error);
            }
            return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
