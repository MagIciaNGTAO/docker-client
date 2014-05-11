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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fitbur.docker.client.internal.CamelMapperSupplier;
import com.fitbur.docker.client.internal.ObjectMapperResolver;
import com.fitbur.docker.client.internal.PascalMapperSupplier;
import com.fitbur.docker.client.topic.ContainerTopic;
import com.fitbur.docker.client.topic.ImageTopic;
import java.net.URI;
import static java.net.URI.create;
import java.util.Optional;
import static java.util.Optional.of;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import static javax.ws.rs.client.ClientBuilder.newClient;
import javax.ws.rs.core.Configuration;
import org.glassfish.hk2.api.messaging.Topic;
import org.glassfish.jersey.client.ClientConfig;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class DockerClientBuilder {

    private final Topic<ContainerTopic> containerTopic;
    private final Topic<ImageTopic> imageTopic;

    @Inject
    public DockerClientBuilder(Topic<ContainerTopic> containerTopic,
            Topic<ImageTopic> imageTopic) {
        this.containerTopic = containerTopic;
        this.imageTopic = imageTopic;
    }

    public UriBuilder uri(String uri) {
        return new UriBuilder(create(uri));
    }

    public UriBuilder uri(URI uri) {
        return new UriBuilder(uri);
    }

    public class UriBuilder {

        private final URI uri;
        private Optional<Configuration> config = of(new ClientConfig());

        public UriBuilder(URI uri) {
            this.uri = uri;
        }

        public UriBuilder config(Configuration config) {
            this.config = of(config);
            return this;
        }

        public DockerClient build() {
            Client client = newClient(config.get());
            ObjectMapper pascalMapper;
            ObjectMapper camelMapper;
            ObjectMapperResolver resolver;

            PropertyNamingStrategy pascal = PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE;
            pascalMapper = new PascalMapperSupplier(pascal).get();
            camelMapper = new CamelMapperSupplier().get();
            resolver = new ObjectMapperResolver(pascalMapper, camelMapper);

            client.register(resolver);

            return new DockerClient(client, uri, containerTopic, imageTopic);
        }
    }
}
