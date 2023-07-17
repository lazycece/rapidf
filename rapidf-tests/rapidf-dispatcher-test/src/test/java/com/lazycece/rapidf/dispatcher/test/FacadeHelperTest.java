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

import com.lazycece.rapidf.dispatcher.core.facade.CommandFacade;
import com.lazycece.rapidf.dispatcher.core.facade.FacadeRegistration;
import com.lazycece.rapidf.dispatcher.exception.DispatchException;
import com.lazycece.rapidf.dispatcher.helper.FacadeHelper;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lazycece
 * @date 2023/7/17
 */
public class FacadeHelperTest {

    @Test
    public void testIsExpectedFacade() {
        assertThat(FacadeHelper.isExpectedFacade(SampleCommandFacadeImpl.class)).isTrue();
        assertThat(FacadeHelper.isExpectedFacade(SampleCommandFacade2.class)).isFalse();
        assertThat(FacadeHelper.isExpectedFacade(SampleCommandFacade2.class)).isFalse();
    }

    @Test
    public void testGetFacadeActionMap() {
        Map<String, FacadeRegistration.FacadeAction> actionMap = FacadeHelper.getFacadeActionMap(SampleCommandFacadeImpl.class);
        assertThat(actionMap).isNotEmpty();
        assertThatThrownBy(() -> FacadeHelper.getFacadeActionMap(SampleCommandFacade2.class))
                .isInstanceOf(DispatchException.class);
        assertThatThrownBy(() -> FacadeHelper.getFacadeActionMap(SampleCommandFacade3.class))
                .isInstanceOf(DispatchException.class);
    }


    public interface SampleCommandFacade extends CommandFacade {

        void sampleHandle(String arg);

    }

    public static class SampleCommandFacadeImpl implements SampleCommandFacade {

        @Override
        public void sampleHandle(String arg) {

        }
    }

    public static class SampleCommandFacade2 implements CommandFacade {

        public void sampleHandle(String arg) {

        }
    }

    public static class SampleCommandFacade3 {

        public void sampleHandle(String arg) {

        }
    }
}
