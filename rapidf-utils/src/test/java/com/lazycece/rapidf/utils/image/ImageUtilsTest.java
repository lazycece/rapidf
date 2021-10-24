package com.lazycece.rapidf.utils.image;

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
