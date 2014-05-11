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
import com.fitbur.docker.client.model.ImageConfig;
import com.fitbur.docker.client.model.ImageStatus;
import com.fitbur.docker.client.request.BiQuery;
import com.fitbur.docker.client.topic.ImageTopic;
import com.fitbur.docker.client.topic.event.ImageEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.apache.avro.Schema;
import org.glassfish.jersey.client.ChunkedInput;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class CreateImage implements BiQuery<ImageConfig, List<ImageStatus>> {

    private final GenericType<ChunkedInput<ImageStatus>> DEFAULT_TYPE = new GenericType<ChunkedInput<ImageStatus>>() {
    };

    @Override
    public List<ImageStatus> execute(DockerClient client,
            Optional<ImageConfig> imageConfig) {
        WebTarget target = client.target()
                .path("images")
                .path("create");
        ImageConfig config = imageConfig.get();

        for (Schema.Field field : config.getSchema().getFields()) {
            Object value = config.get(field.pos());

            if (value == null) {
                continue;
            }

            target = target.queryParam(field.name(), value);
        }

        Response response = target
                .request(APPLICATION_JSON)
                .post(null);

        if (response.getStatus() < 400) {

            List<ImageStatus> statusList;
            try (ChunkedInput<ImageStatus> input = response.readEntity(DEFAULT_TYPE)) {
                input.setChunkType(MediaType.APPLICATION_JSON);
                statusList = new ArrayList<>();
                ImageStatus status;
                while ((status = input.read()) != null) {
                    statusList.add(status);
                }
            }

            client.publish(new ImageTopic(config.getFromImage(), ImageEvent.CREATED));

            return statusList;

        } else {
            throw new DockerClientException(response.readEntity(String.class));
        }

    }
}
