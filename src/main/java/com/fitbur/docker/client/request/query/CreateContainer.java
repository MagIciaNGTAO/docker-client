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
package com.fitbur.docker.client.request.query;

import com.fitbur.docker.client.DockerClient;
import com.fitbur.docker.client.DockerClientException;
import com.fitbur.docker.client.model.ContainerConfig;
import com.fitbur.docker.client.model.CreateResponse;
import com.fitbur.docker.client.request.BiQuery;
import com.fitbur.docker.client.topic.ContainerTopic;
import com.fitbur.docker.client.topic.event.ContainerEvent;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.WebTarget;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class CreateContainer implements BiQuery<ContainerConfig, CreateResponse> {

    @Override
    public CreateResponse execute(DockerClient client,
            Optional<ContainerConfig> createConfig) {
        WebTarget target = client.target()
                .path("containers")
                .path("create");
        ContainerConfig configEntity = createConfig.get();

        for (Map.Entry<String, Object> param : configEntity.getQueryParams().entrySet()) {
            target = target.queryParam(param.getKey(), param.getValue());
        }

        Entity<ContainerConfig> entity = entity(configEntity, APPLICATION_JSON);

        Response response = target
                .request(APPLICATION_JSON)
                .post(entity);

        CreateResponse result;

        if (response.getStatus() < 400) {
            result = response.readEntity(CreateResponse.class);
        } else {
            throw new DockerClientException(response.readEntity(String.class));
        }

        client.publish(new ContainerTopic(result.getId(), ContainerEvent.CREATED));

        return result;
    }
}
