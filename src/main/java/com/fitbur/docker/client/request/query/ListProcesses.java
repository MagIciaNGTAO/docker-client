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
import com.fitbur.docker.client.model.ProcessesResponse;
import com.fitbur.docker.client.request.TriParamQuery;
import java.util.Map;
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
public class ListProcesses implements TriParamQuery<String, ProcessesResponse> {

    @Override
    public ProcessesResponse execute(DockerClient client, Optional<String> containerId, Optional<Map<String, Object>> queryParams) {
        WebTarget target = client.target()
                .path("containers")
                .path(containerId.get())
                .path("top");

        if (queryParams.isPresent()) {
            for (Map.Entry<String, Object> param : queryParams.get().entrySet()) {
                target = target.queryParam(param.getKey(), param.getValue());
            }
        }

        Response response = target.request(APPLICATION_JSON).get();

        if (response.getStatus() < 400) {
            return response.readEntity(ProcessesResponse.class);
        } else {
            throw new DockerClientException(response.readEntity(String.class));
        }

    }

}
