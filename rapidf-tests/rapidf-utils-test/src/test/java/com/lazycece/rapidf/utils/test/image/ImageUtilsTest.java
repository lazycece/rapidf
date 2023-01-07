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

package com.lazycece.rapidf.utils.test.image;

import com.lazycece.rapidf.utils.image.ImageUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lazycece
 * @date 2021/8/7
 */
public class ImageUtilsTest {

    @Test
    public void testCompressImage() throws IOException {
        // case1-byte-null
        assertThat(ImageUtils.compressImage(null, 200, 0.88)).isEqualTo(null);

        // case2-bytes.length < limitSize * 1024
        byte[] bytes = new byte[100 * 1024];
        assertThat(ImageUtils.compressImage(bytes, 200, 0.88)).isEqualTo(bytes);

        // case3-
        String imageUrl = "https://t7.baidu.com/it/u=2942499027,2479446682&fm=193&f=GIF";
        BufferedInputStream bis = new BufferedInputStream(new URL(imageUrl).openStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOUtils.copy(bis, bos);
        bytes = bos.toByteArray();
        assertThat(ImageUtils.compressImage(bytes, 200, 0.88).length).isLessThan(200 * 1024);
        bis.close();

        // case4-accuracy to 0
        ImageUtils.compressImage(bytes, 1, 0.88);
    }
}
