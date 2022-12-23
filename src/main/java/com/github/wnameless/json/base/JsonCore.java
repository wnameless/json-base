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

/**
 * 
 * {@link JsonCore} provides a wrapper to all kinds of JSON parsers.
 * 
 * @author Wei-Ming Wu
 *
 * @param <JVC> the type of a JSON implementation wrapper
 */
public interface JsonCore<JVC extends JsonValueCore<JVC>> {

  /**
   * Creates a {@link JsonValueCore} by given JSON string.
   * 
   * @param json any JSON string
   * @return a {@link JsonValueCore}
   */
  JsonValueCore<JVC> parse(String json);

  /**
   * Creates a {@link JsonValueCore} by given JSON string reader.
   * 
   * @param jsonReader any JSON string reader
   * @return a {@link JsonValueCore}
   * @throws IOException if error occurs during reading
   */
  JsonValueCore<JVC> parse(Reader jsonReader) throws IOException;

}
