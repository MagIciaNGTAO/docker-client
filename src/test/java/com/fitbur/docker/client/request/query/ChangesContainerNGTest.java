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
import com.fitbur.docker.client.model.Change;
import com.fitbur.docker.client.model.CreateResponse;
import java.util.List;
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
public class ChangesContainerNGTest {

    @Inject
    ChangesContainer sut;
    @Inject
    DockerClient client;
    @Inject
    @Named("running")
    CreateResponse response;

    /**
     * Test Inspect command
     */
    @Test
    public void givenContainerIdRequestShouldReturn() {
        List<Change> result = client.request(response.getId(), sut);
        assertThat(result).isNotNull();
    }

}
