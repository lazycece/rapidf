/*
 *    Copyright 2022 lazycece<lazycece@gmail.com>
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

package com.lazycece.rapidf.domain.sample.statemachine.transition;

import com.lazycece.rapidf.domain.statemachine.AbstractTransition;
import com.lazycece.rapidf.domain.statemachine.State;
import com.lazycece.rapidf.domain.statemachine.StateEvent;

/**
 * @author lazycece
 * @date 2022/12/28
 */
public abstract class AbstractGoodsAuditStateTransition extends AbstractTransition {

    protected AbstractGoodsAuditStateTransition(StateEvent<?> stateEvent, State<?> source, State<?> target) {
        super(stateEvent, source, target);
    }
}
