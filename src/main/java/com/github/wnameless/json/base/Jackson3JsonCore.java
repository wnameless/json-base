/*
 *
 * Copyright 2025 Wei-Ming Wu
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
import tools.jackson.databind.ObjectMapper;

/**
 * 
 * The Jackson 3 implementation of {@link JsonCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public class Jackson3JsonCore implements JsonCore<Jackson3JsonValue> {

  private ObjectMapper mapper;

  private static class LazyHolder {
    public static final ObjectMapper INSTANCE = new ObjectMapper();
  }

  private ObjectMapper getInstance() {
    return mapper == null ? LazyHolder.INSTANCE : mapper;
  }

  public Jackson3JsonCore() {
    mapper = null;
  }

  public Jackson3JsonCore(ObjectMapper mapper) {
    if (mapper == null) throw new NullPointerException();
    this.mapper = mapper;
  }

  @Override
  public Jackson3JsonValue parse(String json) {
    return new Jackson3JsonValue(getInstance().readTree(json));
  }

  @Override
  public Jackson3JsonValue parse(Reader jsonReader) throws IOException {
    return new Jackson3JsonValue(getInstance().readTree(jsonReader));
  }

}
