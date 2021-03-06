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
import com.fitbur.docker.client.model.ContainerConfig;
import com.fitbur.docker.client.model.CreateResponse;
import com.fitbur.docker.client.model.HostConfig;
import com.fitbur.docker.client.request.TriCommand;
import com.fitbur.docker.client.request.query.CreateContainer;
import java.util.Optional;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class RunContainer implements TriCommand<ContainerConfig, HostConfig> {

    @Override
    public void execute(DockerClient client,
            Optional<ContainerConfig> createConfig,
            Optional<HostConfig> hostConfig) {

        CreateResponse createResponse
                = client.request(createConfig.get(), new CreateContainer());

        if (hostConfig.isPresent()) {
            client.request(createResponse.getId(), hostConfig.get(), new StartContainer());
        } else {
            client.request(createResponse.getId(), new StartContainer());

        }
    }
}
