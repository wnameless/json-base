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

/**
 * 
 * The Jackson implementation of {@link JsonCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public class JacksonJsonCore implements JsonCore<JacksonJsonValue> {

  private ObjectMapper mapper;

  private static class LazyHolder {
    public static final ObjectMapper INSTANCE = new ObjectMapper();
  }

  private ObjectMapper getInstance() {
    return mapper == null ? LazyHolder.INSTANCE : mapper;
  }

  public JacksonJsonCore() {
    mapper = null;
  }

  public JacksonJsonCore(ObjectMapper mapper) {
    if (mapper == null) throw new NullPointerException();
    this.mapper = mapper;
  }

  @Override
  public JacksonJsonValue parse(String json) {
    try {
      return new JacksonJsonValue(getInstance().readTree(json));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JacksonJsonValue parse(Reader jsonReader) throws IOException {
    return new JacksonJsonValue(getInstance().readTree(jsonReader));
  }

}
