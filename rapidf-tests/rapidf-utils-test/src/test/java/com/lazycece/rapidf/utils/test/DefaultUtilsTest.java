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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lazycece.rapidf.utils.DefaultUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lazycece
 * @date 2021/11/1
 */
public class DefaultUtilsTest {

    @Test
    public void testDefaultValue() {
        assertThat(DefaultUtils.defaultValue("value", "defaultValue"))
                .isEqualTo("value");
        assertThat(DefaultUtils.defaultValue("", "defaultValue"))
                .isEqualTo("defaultValue");
    }

    @Test
    public void testDefaultValueT() {
        Object object = null;
        assertThat(DefaultUtils.defaultValue(object, new Object())).isNotNull();
        object = "object";
        assertThat(DefaultUtils.defaultValue(object, new Object())).isInstanceOf(String.class);
    }

    @Test
    public void testDefaultArray() {
        assertThat(DefaultUtils.defaultArray(null)).isNotNull();
        String[] array = new String[]{"1", "2"};
        assertThat(DefaultUtils.defaultArray(array).size()).isGreaterThan(1);
    }

    @Test
    public void testDefaultList() {
        assertThat(DefaultUtils.defaultList(null)).isNotNull();
        assertThat(DefaultUtils.defaultList(Lists.newArrayList())).isNotNull();
    }

    @Test
    public void testDefaultMap() {
        assertThat(DefaultUtils.defaultMap(null)).isNotNull();
        assertThat(DefaultUtils.defaultMap(Maps.newHashMap())).isNotNull();
    }

    @Test
    public void testDefaultValueIfNullObj() {
        assertThat(DefaultUtils.defaultValueIfNullObj("value", String::toString,
                "defaultValue")).isEqualTo("value");
        assertThat(DefaultUtils.defaultValueIfNullObj(null, String::toString,
                "defaultValue")).isEqualTo("defaultValue");
    }
}
