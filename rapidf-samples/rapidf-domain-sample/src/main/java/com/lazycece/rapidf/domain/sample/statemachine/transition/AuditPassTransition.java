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

import com.lazycece.rapidf.domain.sample.statemachine.AuditStatus;
import com.lazycece.rapidf.domain.sample.statemachine.Goods;
import com.lazycece.rapidf.domain.sample.statemachine.GoodsAuditStateMachine;
import com.lazycece.rapidf.domain.statemachine.StateApply;

/**
 * @author lazycece
 * @date 2022/12/27
 */
public class AuditPassTransition extends AbstractGoodsAuditStateTransition {

    public AuditPassTransition() {
        super(GoodsAuditStateMachine.AuditEvent.AUDIT_PASS, AuditStatus.AUDITING, AuditStatus.PASS);
    }

    @Override
    public void execute(StateApply apply) {
        Goods goods = (Goods) apply;
        System.out.println("goods id : " + goods.getBizId());
        System.out.println("goods status : " + goods.getState().getDesc());
        System.out.println("=============== audit pass ===============");
    }
}
