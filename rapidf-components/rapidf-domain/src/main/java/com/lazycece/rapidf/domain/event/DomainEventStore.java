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

package com.lazycece.rapidf.domain.event;

import java.util.List;

/**
 * The domain event store interface.
 *
 * @author lazycece
 * @date 2023/2/23
 */
public interface DomainEventStore {

    /**
     * To append domain event.
     *
     * @param domainEvent ${@link DomainEvent}
     */
    void append(DomainEvent domainEvent);

    /**
     * Load domain event stream.
     *
     * @param type     event type ${@link DomainEvent#getType()}
     * @param identify event identify ${@link DomainEvent#getIdentity()}
     * @return event stream
     */
    List<DomainEvent> load(String type, String identify);

    /**
     * Load domain event stream.
     *
     * @param type     event type ${@link DomainEvent#getType()}
     * @param identify event identify ${@link DomainEvent#getIdentity()}
     * @param offset   offset
     * @param count    limit count
     * @return event stream
     */
    List<DomainEvent> load(String type, String identify, int offset, int count);

}
