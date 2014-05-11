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
package com.fitbur.docker.client;

import com.fitbur.docker.client.model.ContainerConfig;
import com.fitbur.docker.client.request.BiCommand;
import com.fitbur.docker.client.request.BiQuery;
import com.fitbur.docker.client.request.TriCommand;
import com.fitbur.docker.client.request.TriQuery;
import com.fitbur.docker.client.topic.ContainerTopic;
import com.fitbur.docker.client.topic.ImageTopic;
import java.net.URI;
import java.util.Map;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.glassfish.hk2.api.messaging.Topic;

/**
 *
 * @author Sharmarke Aden
 */
public class DockerClient {

    private final Client client;
    private final URI uri;
    private final Topic<ContainerTopic> containerTopic;
    private final Topic<ImageTopic> imageTopic;

    public DockerClient(Client client,
            URI uri,
            Topic<ContainerTopic> containerTopic,
            Topic<ImageTopic> imageTopic) {
        this.client = client;
        this.uri = uri;
        this.containerTopic = containerTopic;
        this.imageTopic = imageTopic;
    }

    public Client client() {
        return client;
    }

    public URI uri() {
        return uri;
    }

    public WebTarget target() {
        return client.target(uri);
    }

    public void close() {
        client.close();
    }

    public void publish(ContainerTopic topic) {
        if (containerTopic != null) {
            containerTopic.publish(topic);
        }
    }

    public void publish(ImageTopic topic) {
        if (imageTopic != null) {
            imageTopic.publish(topic);
        }
    }

    public <T> void request(BiCommand<T> command) {
        try {
            command.execute(this, empty());
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T> void request(T t, BiCommand<T> command) {
        try {
            command.execute(this, of(t));
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, U> void request(TriCommand<T, U> command) {
        try {
            command.execute(this, empty(), empty());
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, U> void request(T t, TriCommand<T, U> command) {
        try {
            command.execute(this, of(t), empty());
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, U> void request(T t, U u, TriCommand<T, U> command) {
        try {
            command.execute(this, of(t), of(u));
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T> void request(T t, Map<String, Object> queryParams, TriCommand<T, Map<String, Object>> command) {
        try {
            command.execute(this, of(t), of(queryParams));
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, R> R request(BiQuery<T, R> query) {
        try {
            return query.execute(this, empty());
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, R> R request(T t, BiQuery<T, R> query) {
        try {
            return query.execute(this, of(t));
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, U, R> R request(TriQuery<T, U, R> query) {
        try {
            return query.execute(this, empty(), empty());
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, U, R> R request(T t, TriQuery<T, U, R> query) {
        try {
            return query.execute(this, of(t), empty());
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public <T, R> R request(T t, Map<String, Object> queryParams, TriQuery<T, Map<String, Object>, R> query) {
        try {
            return query.execute(this, of(t), of(queryParams));
        }
        catch (Exception e) {
            throw new DockerClientException(e);
        }
    }

    public void run(ContainerConfig config) {

    }

    public void create(ContainerConfig config) {

    }

    public void start(ContainerConfig config) {

    }

    public void stop(ContainerConfig config) {

    }

    public void restart(ContainerConfig config) {

    }

    public void remove(ContainerConfig config) {

    }

    public void inspect(ContainerConfig config) {

    }

    public void list(ContainerConfig config) {

    }

    public void images(ContainerConfig config) {

    }

    public void search(ContainerConfig config) {

    }

    public void make(ContainerConfig config) {

    }

    public void kill(ContainerConfig config) {

    }

    public void commit(ContainerConfig config) {

    }

    public void build(ContainerConfig config) {

    }

    public void wait(ContainerConfig config) {

    }

    public void logs(ContainerConfig config) {

    }

    public void changes(ContainerConfig config) {

    }

}
