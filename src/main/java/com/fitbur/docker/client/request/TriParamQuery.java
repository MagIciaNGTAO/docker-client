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
package com.fitbur.docker.client.request;

import com.fitbur.docker.client.DockerClient;
import java.util.Map;
import java.util.Optional;
import org.jvnet.hk2.annotations.Contract;

/**
 *
 * @author Sharmarke Aden
 * @param <T>
 * @param <R>
 */
@Contract
@FunctionalInterface
public interface TriParamQuery<T, R> extends TriQuery<T, Map<String, Object>, R> {

    @Override
    R execute(DockerClient client, Optional<T> entity, Optional<Map<String, Object>> queryParams);

}
