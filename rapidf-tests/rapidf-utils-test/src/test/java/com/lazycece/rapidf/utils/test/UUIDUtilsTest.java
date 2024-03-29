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

package com.lazycece.rapidf.utils.test;

import com.lazycece.rapidf.utils.UUIDUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lazycece
 * @date 2021/10/30
 */
public class UUIDUtilsTest {

    @Test
    public void testUUID() {
        String uuid = UUIDUtils.uuid();
        assertThat(uuid).isNotBlank();
    }

    @Test
    public void testSn() {
        String sn = UUIDUtils.sn(6);
        assertThat(sn.length()).isEqualTo(6);
    }
}
