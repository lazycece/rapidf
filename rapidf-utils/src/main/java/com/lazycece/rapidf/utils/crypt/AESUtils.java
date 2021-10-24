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

package com.lazycece.rapidf.utils.crypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * AES ECB encrypt and decrypt
 *
 * @author lazycece
 * @date 2019/11/20
 */
public class AESUtils {

    public static byte[] encrypt(final String secret, String data) throws Exception {
        return crypto(secret, Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decrypt(final String secret, byte[] data) throws Exception {
        return crypto(secret, Cipher.DECRYPT_MODE, data);
    }

    private static byte[] crypto(final String secret, int mode, byte[] data) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secret.getBytes(StandardCharsets.UTF_8));
        generator.init(128, random);
        SecretKey aesKey = new SecretKeySpec(generator.generateKey().getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, aesKey);
        return cipher.doFinal(data);
    }
}
