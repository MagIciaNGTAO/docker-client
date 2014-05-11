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
import com.fitbur.docker.client.DockerClientBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.client.ClientConfig;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden
 */
@Service
public class DockerClientProvider implements Factory<DockerClient> {

    private final DockerClientBuilder builder;

    @Inject
    DockerClientProvider(DockerClientBuilder builder) {
        this.builder = builder;
    }

    @Singleton
    @Override
    public DockerClient provide() {
        return builder.uri("http://localhost:4243/v1.11")
                .config(new ClientConfig())
                .build();
    }

    @Override
    public void dispose(DockerClient client) {
        client.close();
    }

}
