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
package com.fitbur.docker.client.request.command;

import com.fitbur.docker.client.DockerClient;
import com.fitbur.docker.client.DockerClientException;
import com.fitbur.docker.client.request.TriCommand;
import com.fitbur.docker.client.topic.ContainerTopic;
import com.fitbur.docker.client.topic.event.ContainerEvent;
import java.util.Optional;
import javax.ws.rs.client.WebTarget;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class StopContainer implements TriCommand<String, Long> {

    @Override
    public void execute(DockerClient client,
            Optional<String> containerId,
            Optional<Long> time) {

        WebTarget target = client.target()
                .path("containers")
                .path(containerId.get())
                .path("stop");

        if (time.isPresent()) {
            target = target.queryParam("time", time.get());
        }

        Response response = target
                .request(APPLICATION_JSON)
                .post(null);

        if (response.getStatus() >= 400) {
            throw new DockerClientException(response.readEntity(String.class));
        }

        client.publish(new ContainerTopic(containerId.get(), ContainerEvent.STOPPED));

    }
}
