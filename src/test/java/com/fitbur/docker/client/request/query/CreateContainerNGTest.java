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
import com.fitbur.docker.client.model.ContainerConfig;
import static com.fitbur.docker.client.model.ContainerConfig.newBuilder;
import com.fitbur.docker.client.model.CreateResponse;
import static java.util.Arrays.asList;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden
 */
@HK2
public class CreateContainerNGTest {

    @Inject
    CreateContainer sut;

    @Inject
    DockerClient client;

    /**
     * Test of execute method, of class ListCommand.
     */
    @Test
    public void testExecute() {
        ContainerConfig.Builder builder = newBuilder()
                .setImage("busybox")
                .setCmd(asList(new String[]{"true"}));

        CreateResponse result = client.request(builder.build(), sut);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotEmpty();
        assertThat(result.getWarnings()).isNull();
    }

}
