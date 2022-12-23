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

/**
 * 
 * {@link JsonArrayCore} extends {@link JsonArrayBase}, {@link JsonValueCore} and adds few methods
 * to make it mutable.
 * 
 * @author Wei-Ming Wu
 *
 * @param <JVC> the type of a JSON implementation wrapper
 */
public interface JsonArrayCore<JVC extends JsonValueCore<JVC>>
    extends JsonArrayBase<JVC>, JsonValueCore<JVC> {

  /**
   * Sets an element by given {@link JsonSource}.
   * 
   * @param jsonSource a {@link JsonSource}
   */
  void add(JsonSource jsonSource);

  /**
   * Sets an element by given index and {@link JsonSource}.
   * 
   * @param index a position in this JSON array
   * @param jsonSource a {@link JsonSource}
   */
  void set(int index, JsonSource jsonSource);

  /**
   * Removes an element by given index.
   * 
   * @param index a position in this JSON array
   * @return the removed element
   */
  JVC remove(int index);

}
