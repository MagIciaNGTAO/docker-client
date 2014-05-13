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
package com.fitbur.docker.client.fixture;

import com.fitbur.docker.client.DockerClient;
import com.fitbur.docker.client.topic.ContainerTopic;
import com.fitbur.docker.client.topic.ImageTopic;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Immediate
@Service
public class TestCleanupService {

    Set<String> containerIds;
    Set<String> imageNames;
    private final DockerClient client;

    @Inject
    TestCleanupService(DockerClient client) {
        this.client = client;
    }

    @PostConstruct
    void init() {
        containerIds = new HashSet<>();
        imageNames = new HashSet<>();
    }

    @PreDestroy
    void cleanupContainers() {
        containerIds.stream()
                .forEach((containerId) -> {
                    System.out.println("Stopping Container: " + containerId);

                    client.target()
                    .path("containers")
                    .path(containerId)
                    .path("stop")
                    .request(APPLICATION_JSON)
                    .post(null);

                    System.out.println("Removing Container: " + containerId);

                    client.target()
                    .path("containers")
                    .path(containerId)
                    .request(APPLICATION_JSON)
                    .delete();
                });

        imageNames.stream()
                .forEach((imageName) -> {
                    System.out.println("Removing Image: " + imageName);

                    client.target()
                    .path("images")
                    .path(imageName)
                    .request(APPLICATION_JSON)
                    .delete();
                });
    }

    public void containerTopic(@SubscribeTo ContainerTopic topic) {
        switch (topic.getEvent()) {
            case CREATED:
                containerIds.add(topic.getId());
                break;
        }
    }

    public void imageTopic(@SubscribeTo ImageTopic topic) {
        switch (topic.getEvent()) {
            case CREATED:
                imageNames.add(topic.getName());
                break;
        }
    }
}
