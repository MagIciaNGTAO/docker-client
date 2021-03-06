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

import com.fitbur.docker.client.topic.event.ImageEvent;
import java.util.Objects;

/**
 *
 * @author Sharmarke Aden
 */
public class ImageTopic {

    private final String name;
    private final ImageEvent event;

    public ImageTopic(String name, ImageEvent event) {
        this.name = name;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public ImageEvent getEvent() {
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
        final ImageTopic other = (ImageTopic) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        return this.event == other.event;
    }

    @Override
    public String toString() {
        return "ImageTopic{" + "name=" + name + ", event=" + event + '}';
    }

}
