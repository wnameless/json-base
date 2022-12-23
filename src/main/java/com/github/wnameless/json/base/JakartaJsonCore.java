/*
 *
 * Copyright 2022 Wei-Ming Wu
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
import java.io.StringReader;
import jakarta.json.Json;
import jakarta.json.JsonReaderFactory;

/**
 * 
 * The Jakarta implementation of {@link JsonCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public class JakartaJsonCore implements JsonCore<JakartaJsonValue> {

  private final JsonReaderFactory factory;

  public JakartaJsonCore() {
    factory = null;
  }

  public JakartaJsonCore(JsonReaderFactory factory) {
    if (factory == null) throw new NullPointerException();
    this.factory = factory;
  }

  @Override
  public JakartaJsonValue parse(String json) {
    if (factory != null) {
      return new JakartaJsonValue(factory.createReader(new StringReader(json)).readValue());
    }
    return new JakartaJsonValue(Json.createReader(new StringReader(json)).readValue());
  }

  @Override
  public JakartaJsonValue parse(Reader jsonReader) throws IOException {
    if (factory != null) {
      return new JakartaJsonValue(factory.createReader(jsonReader).readValue());
    }
    return new JakartaJsonValue(Json.createReader(jsonReader).readValue());
  }

}
