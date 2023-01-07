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

package com.lazycece.rapidf.domain.statemachine;

import com.lazycece.rapidf.domain.statemachine.exception.StateMachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author lazycece
 * @date 2022/12/11
 */
public class AbstractStateMachine implements StateMachine {

    private static final Logger log = LoggerFactory.getLogger(AbstractTransition.class);

    private final Map<State<?>, List<Transition>> transitionMap = new HashMap<>();

    protected AbstractStateMachine(List<? extends Transition> transitions) {
        Objects.requireNonNull(transitions, "transitions is required");
        transitions.forEach(transition -> {
            if (!transitionMap.containsKey(transition.getSourceState())) {
                transitionMap.put(transition.getSourceState(), new ArrayList<>());
            }
            transitionMap.get(transition.getSourceState()).add(transition);
        });
    }

    /**
     * @see StateMachine#execute
     */
    @Override
    public State<?> execute(StateApply apply, StateEvent<?> stateEvent) {
        Objects.requireNonNull(apply, "apply is required");
        Objects.requireNonNull(stateEvent, "stateEvent is required");

        State<?> source = apply.getState();
        if (source == null) {
            throw new StateMachineException("Apply state is null");
        }

        List<Transition> transitionList = transitionMap.get(source);
        if (transitionList == null || transitionList.size() == 0) {
            throw new StateMachineException(String.format("Cannot find transitions, source state is %s.", source.getDesc()));
        }
        Optional<Transition> optional = transitionList.stream()
                .filter(transition -> stateEvent == transition.getStateEvent())
                .findFirst();
        if (!optional.isPresent()) {
            throw new StateMachineException(String.format("Cannot find a transition, source state is %s, state event is %s.",
                    source.getDesc(), stateEvent.getDesc()));
        }

        Transition transition = optional.get();
        log.info("{} state transition, source state is {}, target state is {}.",
                stateEvent.getDesc(), transition.getSourceState().getDesc(),
                transition.getTargetState().getDesc());
        transition.execute(apply);

        return transition.getTargetState();
    }
}
