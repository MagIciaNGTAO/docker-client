/*
 * Copyright 2014 Fitbur.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fitbur.docker.client.topic;

import com.fitbur.docker.client.topic.event.ContainerEvent;
import java.util.Objects;

/**
 *
 * @author Sharmarke Aden
 */
public class ContainerTopic {

    private final String id;
    private final ContainerEvent event;

    public ContainerTopic(String id, ContainerEvent event) {
        this.id = id;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public ContainerEvent getEvent() {
        return event;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContainerTopic other = (ContainerTopic) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        return this.event == other.event;
    }

    @Override
    public String toString() {
        return "ContainerTopic{" + "id=" + id + ", event=" + event + '}';
    }

}
