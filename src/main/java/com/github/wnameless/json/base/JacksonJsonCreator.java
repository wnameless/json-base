/*
 *
 * Copyright 2020 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.github.wnameless.json.base;

import java.io.IOException;
import java.io.Reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonCreator implements JsonCreator<JacksonJsonValue> {

  private ObjectMapper mapper;

  public JacksonJsonCreator(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public JacksonJsonValue parse(String json) {
    try {
      return new JacksonJsonValue(mapper.readTree(json));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JacksonJsonValue parse(Reader jsonReader) throws IOException {
    return new JacksonJsonValue(mapper.readTree(jsonReader));
  }

  @Override
  public JacksonJsonArray createJsonArray() {
    return new JacksonJsonArray(mapper.createArrayNode());
  }

  @Override
  public JacksonJsonObject createJsonObject() {
    return new JacksonJsonObject(mapper.createObjectNode());
  }

}
