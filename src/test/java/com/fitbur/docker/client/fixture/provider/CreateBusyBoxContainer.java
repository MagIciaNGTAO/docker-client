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
package com.fitbur.docker.client.fixture.provider;

import com.fitbur.docker.client.DockerClient;
import com.fitbur.docker.client.model.ContainerConfig;
import static com.fitbur.docker.client.model.ContainerConfig.newBuilder;
import com.fitbur.docker.client.model.CreateResponse;
import com.fitbur.docker.client.model.ImageStatus;
import com.fitbur.docker.client.topic.ContainerTopic;
import com.fitbur.docker.client.topic.event.ContainerEvent;
import static java.util.Arrays.asList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class CreateBusyBoxContainer implements Factory<CreateResponse> {

    private final DockerClient client;
    private final List<ImageStatus> status;

    @Inject
    CreateBusyBoxContainer(DockerClient client,
            @Named("busybox") List status) {
        this.client = client;
        this.status = status;
    }

    @Override
    public CreateResponse provide() {
        ContainerConfig ContainerConfig = newBuilder()
                .setImage("busybox")
                .setCmd(asList(new String[]{"sleep", "300"}))
                .build();

        WebTarget target = client.target()
                .path("containers")
                .path("create");

        Entity<ContainerConfig> entity = entity(ContainerConfig, MediaType.APPLICATION_JSON);
        CreateResponse response = target.request(MediaType.APPLICATION_JSON)
                .post(entity, CreateResponse.class);

        client.publish(new ContainerTopic(response.getId(), ContainerEvent.CREATED));

        return response;
    }

    @Override
    public void dispose(CreateResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
