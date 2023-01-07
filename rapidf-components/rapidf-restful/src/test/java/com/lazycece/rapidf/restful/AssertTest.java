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

package com.lazycece.rapidf.restful;

import com.lazycece.rapidf.restful.exception.AssertException;
import com.lazycece.rapidf.restful.response.RespStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lazycece
 * @date 2021/11/4
 */
public class AssertTest {

    @Test
    public void testIsTrue() {
        String msgFormat = "Assert.%s error";
        Assert.isTrue(true, RespStatus.FAIL, msgFormat, "isTrue");
        assertThatThrownBy(() ->
                Assert.isTrue(false, RespStatus.FAIL, msgFormat, "isTrue"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "isTrue"));
    }

    @Test
    public void testIsFalse() {
        String msgFormat = "Assert.%s error";
        Assert.isFalse(false, RespStatus.FAIL, msgFormat, "isFalse");
        assertThatThrownBy(() ->
                Assert.isFalse(true, RespStatus.FAIL, msgFormat, "isFalse"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "isFalse"));
    }

    @Test
    public void testNotBlank() {
        String msgFormat = "Assert.%s error";
        Assert.notBlank("--", RespStatus.FAIL, msgFormat, "notBlank");
        assertThatThrownBy(() ->
                Assert.notBlank(" ", RespStatus.FAIL, msgFormat, "notBlank"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "notBlank"));
    }

    @Test
    public void testIsBlank() {
        String msgFormat = "Assert.%s error";
        Assert.isBlank("", RespStatus.FAIL, msgFormat, "isBlank");
        assertThatThrownBy(() ->
                Assert.isBlank("--", RespStatus.FAIL, msgFormat, "isBlank"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "isBlank"));
    }

    @Test
    public void testNotNull() {
        String msgFormat = "Assert.%s error";
        Assert.notNull(new Object(), RespStatus.FAIL, msgFormat, "notNull");
        assertThatThrownBy(() ->
                Assert.notNull(null, RespStatus.FAIL, msgFormat, "notNull"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "notNull"));
    }

    @Test
    public void testIsNull() {
        String msgFormat = "Assert.%s error";
        Assert.isNull(null, RespStatus.FAIL, msgFormat, "isNull");
        assertThatThrownBy(() ->
                Assert.isNull(new Object(), RespStatus.FAIL, msgFormat, "isNull"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "isNull"));
    }

    @Test
    public void testNotEmpty() {
        String msgFormat = "Assert.%s error";
        Assert.notEmpty(Collections.singletonList(1), RespStatus.FAIL, msgFormat, "notEmpty");
        assertThatThrownBy(() ->
                Assert.notEmpty(null, RespStatus.FAIL, msgFormat, "notEmpty"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "notEmpty"));
    }

    @Test
    public void testIsEmpty() {
        String msgFormat = "Assert.%s error";
        Assert.isEmpty(new ArrayList<>(), RespStatus.FAIL, msgFormat, "isEmpty");
        assertThatThrownBy(() ->
                Assert.isEmpty(Collections.singletonList(1), RespStatus.FAIL, msgFormat, "isEmpty"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "isEmpty"));
    }

    @Test
    public void testEqual() {
        String msgFormat = "Assert.%s error";
        Assert.equal("a", "a", RespStatus.FAIL, msgFormat, "equal");
        assertThatThrownBy(() ->
                Assert.equal(1, 2, RespStatus.FAIL, msgFormat, "equal"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "equal"));
    }

    @Test
    public void testAssignableFrom() {
        String msgFormat = "Assert.%s error";
        Assert.assignableFrom(Collection.class, List.class, RespStatus.FAIL, msgFormat, "assignableFrom");
        assertThatThrownBy(() ->
                Assert.assignableFrom(List.class, Collection.class, RespStatus.FAIL, msgFormat, "assignableFrom"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "assignableFrom"));
    }

    @Test
    public void testLess() {
        String msgFormat = "Assert.%s error";
        Assert.less(1, 2, RespStatus.FAIL, msgFormat, "less");
        assertThatThrownBy(() ->
                Assert.less(2, 1, RespStatus.FAIL, msgFormat, "less"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "less"));
    }

    @Test
    public void testLessOrEqual() {
        String msgFormat = "Assert.%s error";
        Assert.lessOrEqual(1, 1, RespStatus.FAIL, msgFormat, "lessOrEqual");
        assertThatThrownBy(() ->
                Assert.lessOrEqual(2, 1, RespStatus.FAIL, msgFormat, "lessOrEqual"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "lessOrEqual"));
    }

    @Test
    public void testGreater() {
        String msgFormat = "Assert.%s error";
        Assert.greater(2, 1, RespStatus.FAIL, msgFormat, "greater");
        assertThatThrownBy(() ->
                Assert.greater(1, 2, RespStatus.FAIL, msgFormat, "greater"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "greater"));
    }

    @Test
    public void testGreaterOrEqual() {
        String msgFormat = "Assert.%s error";
        Assert.greaterOrEqual(1, 1, RespStatus.FAIL, msgFormat, "greaterOrEqual");
        assertThatThrownBy(() ->
                Assert.greaterOrEqual(1, 2, RespStatus.FAIL, msgFormat, "greaterOrEqual"))
                .isInstanceOf(AssertException.class)
                .hasMessageContaining(String.format(msgFormat, "greaterOrEqual"));
    }
}
