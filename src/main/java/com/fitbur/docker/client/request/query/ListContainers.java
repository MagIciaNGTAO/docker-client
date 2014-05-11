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
import com.fitbur.docker.client.model.Container;
import com.fitbur.docker.client.request.BiQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class ListContainers implements BiQuery<Map<String, Object>, List<Container>> {

    @Override
    public List<Container> execute(DockerClient client, Optional<Map<String, Object>> queryParams) {
        WebTarget target = client.target()
                .path("containers")
                .path("json");

        if (queryParams.isPresent()) {
            for (Map.Entry<String, Object> param : queryParams.get().entrySet()) {
                target = target.queryParam(param.getKey(), param.getValue());
            }
        } else {
            target = target.queryParam("all", 1);
        }

        return target
                .request(APPLICATION_JSON)
                .get(new GenericType<List<Container>>() {
                });
    }

}
