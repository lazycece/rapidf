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

package com.lazycece.rapidf.domain.event.impl;

import com.lazycece.rapidf.domain.event.DomainEvent;
import com.lazycece.rapidf.domain.event.DomainEventStore;
import com.lazycece.rapidf.domain.event.exception.DomainEventException;

import java.util.List;

/**
 * The default domain event store.
 *
 * @author lazycece
 * @date 2023/3/12
 */
public class DefaultDomainEventStore implements DomainEventStore {

    /**
     * @see DomainEventStore#append
     */
    @Override
    public void append(DomainEvent domainEvent) {
        // empty implement.
    }

    /**
     * @see DomainEventStore#load
     */
    @Override
    public List<DomainEvent> load(String type, String identify) {
        throw new DomainEventException("Not support in default domain event store.");
    }

    /**
     * @see DomainEventStore#load
     */
    @Override
    public List<DomainEvent> load(String type, String identify, int offset, int count) {
        throw new DomainEventException("Not support in default domain event store.");
    }
}
