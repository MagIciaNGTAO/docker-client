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
import com.fitbur.docker.client.model.ImageStatus;
import com.fitbur.docker.client.topic.ImageTopic;
import com.fitbur.docker.client.topic.event.ImageEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.client.ChunkedInput;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class CreateBusyBoxImage implements Factory<List> {

    private final DockerClient client;

    @Inject
    CreateBusyBoxImage(DockerClient client) {
        this.client = client;
    }

    @Named("busybox")
    @Override
    public List provide() {
        Response response = client.target()
                .path("images")
                .path("create")
                .queryParam("fromImage", "busybox")
                .request(APPLICATION_JSON)
                .post(null);

        response.bufferEntity();
        GenericType<ChunkedInput<ImageStatus>> typeInfo = new GenericType<ChunkedInput<ImageStatus>>() {
        };
        List<ImageStatus> statusList;
        try (ChunkedInput<ImageStatus> input = response.readEntity(typeInfo)) {
            input.setChunkType(MediaType.APPLICATION_JSON);
            statusList = new ArrayList<>();
            ImageStatus status;
            while ((status = input.read()) != null) {
                statusList.add(status);
            }
        }

        client.publish(new ImageTopic("busybox", ImageEvent.CREATED));

        return statusList;
    }

    @Override
    public void dispose(List input) {
    }

}
