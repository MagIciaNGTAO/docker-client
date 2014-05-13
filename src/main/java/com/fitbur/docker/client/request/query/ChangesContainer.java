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
import com.fitbur.docker.client.model.Change;
import com.fitbur.docker.client.request.BiQuery;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class ChangesContainer implements BiQuery<String, List<Change>> {

    @Override
    public List<Change> execute(DockerClient client,
            Optional<String> containerId) {

        WebTarget target = client.target()
                .path("containers")
                .path(containerId.get())
                .path("changes");

        Response response = target
                .request(APPLICATION_JSON)
                .get();

        List<Change> changes;

        if (response.getStatus() < 400) {
            changes = response.readEntity(new GenericType<List<Change>>() {
            });

        } else {
            throw new DockerClientException(response.readEntity(String.class));
        }

        return changes;
    }
}
