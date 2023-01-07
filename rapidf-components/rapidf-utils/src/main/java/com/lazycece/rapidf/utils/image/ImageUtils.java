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

package com.lazycece.rapidf.utils.image;

import com.lazycece.rapidf.utils.constants.CommonConstants;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author lazycece
 * @date 2021/8/7
 */
public class ImageUtils {
    /**
     * image compress
     *
     * <p> 1. After the specified quality compression is completed,
     * the file size limit is not met, and the quality will continue
     * to be reduced values are compressed，<code>accuracy = accuracy
     * * accuracy</code>
     * <p> 2. When the accuracy reaches the minimum to 0, the result will be returned directly
     * <p> 3. Output picture in jpg format
     *
     * @param bytes     picture byte stream
     * @param limitSize limit size kb
     * @param accuracy  Basic compression accuracy,[0,1]
     * @return picture byte stream
     * @throws IOException IOException
     */
    public static byte[] compressImage(byte[] bytes, long limitSize,
                                       double accuracy) throws IOException {
        if (bytes == null || bytes.length < limitSize * CommonConstants.ONE_K_BYTES) {
            return bytes;
        }
        byte[] transitBytes = bytes;
        while (transitBytes.length > limitSize * CommonConstants.ONE_K_BYTES) {
            ByteArrayInputStream bis = new ByteArrayInputStream(transitBytes);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Thumbnails.of(bis).scale(1).outputFormat("jpg").outputQuality(accuracy)
                    .toOutputStream(bos);
            transitBytes = bos.toByteArray();
            if (accuracy == 0) {
                break;
            }
            // accuracy极限缩小
            accuracy = accuracy * accuracy;
            accuracy = accuracy < 0.01 ? 0 : accuracy;
        }
        return transitBytes;
    }
}
