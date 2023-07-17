/*
 *    Copyright 2023 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.dispatcher.test;

import com.lazycece.rapidf.dispatcher.core.service.CommandHandler;
import com.lazycece.rapidf.dispatcher.exception.DispatchException;
import com.lazycece.rapidf.dispatcher.helper.ServiceHelper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lazycece
 * @date 2023/7/17
 */
public class ServiceHelperTest {

    @Test
    public void testIsExpectedServiceHandler() {
        assertThat(ServiceHelper.isExpectedServiceHandler(SampleHandler.class)).isTrue();
        assertThat(ServiceHelper.isExpectedServiceHandler(SampleHandler2.class)).isFalse();
        assertThat(ServiceHelper.isExpectedServiceHandler(CommandHandler.class)).isFalse();
    }

    @Test
    public void testGetRequestClass() {
        assertThat(ServiceHelper.getRequestClass(SampleHandler.class)).isEqualTo(String.class);
        assertThatThrownBy(() -> ServiceHelper.getRequestClass(SampleHandler2.class))
                .isInstanceOf(DispatchException.class);
    }

    public static class SampleHandler implements CommandHandler<String, String> {

        @Override
        public String handle(String s) {
            return null;
        }
    }

    public static class SampleHandler2 {

        public String handle(String s) {
            return null;
        }
    }
}
