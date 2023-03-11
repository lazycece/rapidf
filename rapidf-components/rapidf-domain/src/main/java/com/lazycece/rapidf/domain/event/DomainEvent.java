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

package com.lazycece.rapidf.domain.event;

import com.lazycece.rapidf.domain.model.Identity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The domain event definition.
 *
 * @author lazycece
 * @date 2022/12/11
 */
public class DomainEvent implements Identity<String> {

    /**
     * The event id, default is uuid.
     */
    private String eventId = UUID.randomUUID().toString();
    /**
     * The event time, you needn't cover it.
     */
    private long timestamp = System.currentTimeMillis() / 1000;
    /**
     * The event source, default is empty string, you can cover it.
     */
    private String source;
    /**
     * The event type, default is empty string, you can cover it.
     */
    private String type = "";
    /**
     * The event version, default is "1.0.0", you can cover it.
     */
    private String version = "1.0.0";
    /**
     * The event extension data.
     */
    private Map<String, Object> extensions = new HashMap<>();
    /**
     * The event business data.
     */
    private Object data;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getId() {
        return eventId;
    }
}
