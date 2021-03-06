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
package com.fitbur.docker.client.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitbur.docker.client.model.ImageStatus;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Sharmarke Aden
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper pascalMapper;
    private final ObjectMapper camelMapper;

    public ObjectMapperResolver(ObjectMapper pascalMapper,
            ObjectMapper camelMapper) {
        this.pascalMapper = pascalMapper;
        this.camelMapper = camelMapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        if (type.isAssignableFrom(ImageStatus.class)) {
            return camelMapper;
        }

        return pascalMapper;
    }

}
