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

import java.util.Map;

/**
 * The domain event model(${@link DomainEvent}) builder, we recommend you to use it.
 *
 * @author lazycece
 * @date 2023/3/11
 */
public class DomainEventBuilder {

    private final DomainEvent event;

    private DomainEventBuilder() {
        this.event = new DomainEvent();
    }

    public static DomainEventBuilder builder() {
        return new DomainEventBuilder();
    }

    public DomainEvent build() {
        return this.event;
    }

    public DomainEventBuilder eventId(String eventId) {
        this.event.setEventId(eventId);
        return this;
    }

    public DomainEventBuilder timestamp(long timestamp) {
        this.event.setTimestamp(timestamp);
        return this;
    }

    public DomainEventBuilder source(String source) {
        this.event.setSource(source);
        return this;
    }

    public DomainEventBuilder type(String type) {
        this.event.setType(type);
        return this;
    }

    public DomainEventBuilder version(String version) {
        this.event.setVersion(version);
        return this;
    }

    public DomainEventBuilder extensions(Map<String, Object> extensions) {
        this.event.getExtensions().putAll(extensions);
        return this;
    }

    public DomainEventBuilder extension(String key, Object value) {
        this.event.getExtensions().put(key, value);
        return this;
    }

    public DomainEventBuilder data(Object data) {
        this.event.setData(data);
        return this;
    }
}
