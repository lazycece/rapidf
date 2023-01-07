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

package com.lazycece.rapidf.domain.sample.statemachine;

import com.lazycece.rapidf.domain.sample.statemachine.transition.AbstractGoodsAuditStateTransition;
import com.lazycece.rapidf.domain.statemachine.AbstractStateMachine;
import com.lazycece.rapidf.domain.statemachine.StateEvent;

import java.util.List;

/**
 * @author lazycece
 * @date 2022/12/27
 */
public class GoodsAuditStateMachine extends AbstractStateMachine {

    public GoodsAuditStateMachine(List<AbstractGoodsAuditStateTransition> transitions) {
        super(transitions);

    }

    public enum AuditEvent implements StateEvent<String> {

        SUBMIT_AUDIT("SUBMIT_AUDIT", "提交审核"),
        AUDIT_PASS("AUDIT_PASS", "审核通过"),
        AUDIT_REJECT("AUDIT_REJECT", "审核拒绝");

        private final String code;
        private final String desc;

        AuditEvent(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDesc() {
            return desc;
        }
    }
}
