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
import com.fitbur.docker.client.model.CreateResponse;
import java.io.ByteArrayOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import static org.assertj.core.api.Assertions.assertThat;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden
 */
@HK2
public class ExportContainerNGTest {

    @Inject
    ExportContainer sut;
    @Inject
    DockerClient client;
    @Named("running")
    @Inject
    CreateResponse createResponse;

    @Test
    public void givenContainerIdRequestShouldStopContainer() {
        ByteArrayOutputStream output = new ByteArrayOutputStream(0x800000);
        client.request(createResponse.getId(), output, sut);
        assertThat(output).isNotNull();
        assertThat(output.size()).isNotZero();
    }

}
