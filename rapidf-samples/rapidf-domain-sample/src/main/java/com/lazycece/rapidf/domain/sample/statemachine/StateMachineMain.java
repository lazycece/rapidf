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
import com.lazycece.rapidf.domain.sample.statemachine.transition.AuditPassTransition;
import com.lazycece.rapidf.domain.sample.statemachine.transition.AuditRejectTransition;
import com.lazycece.rapidf.domain.sample.statemachine.transition.AuditSubmitTransition;
import com.lazycece.rapidf.domain.statemachine.State;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author lazycece
 * @date 2022/12/27
 */
public class StateMachineMain {

    public static void main(String[] args) {

        PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));

        List<AbstractGoodsAuditStateTransition> transitions = new ArrayList<>();
        transitions.add(new AuditSubmitTransition());
        transitions.add(new AuditPassTransition());
        transitions.add(new AuditRejectTransition());

        GoodsAuditStateMachine stateMachine = new GoodsAuditStateMachine(transitions);

        Goods goods = new Goods();
        goods.setGoodsId(UUID.randomUUID().toString().replaceAll("-", ""));
        goods.setStatus(AuditStatus.DRAFT);
        State<?> state = stateMachine.execute(goods, GoodsAuditStateMachine.AuditEvent.SUBMIT_AUDIT);

        goods.setStatus((AuditStatus) state);
        state = stateMachine.execute(goods, GoodsAuditStateMachine.AuditEvent.AUDIT_PASS);

        System.out.println("goods id : " + goods.getBizId());
        System.out.println("goods status : " + state.getDesc());
    }
}
