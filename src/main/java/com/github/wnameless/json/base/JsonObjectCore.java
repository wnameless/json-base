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
 * {@link JsonObjectCore} extends {@link JsonObjectBase}, {@link JsonValueCore}
 * and adds few methods to make it mutable.
 * 
 * @author Wei-Ming Wu
 *
 * @param <JVC>
 *          the type of a JSON implementation wrapper
 */
public interface JsonObjectCore<JVC extends JsonValueCore<JVC>>
    extends JsonObjectBase<JVC>, JsonValueCore<JVC> {

  /**
   * Sets an element by given field name and {@link JsonSource}.
   * 
   * @param name
   *          a field name
   * @param jsonSource
   *          a {@link JsonSource}
   */
  void set(String name, JsonSource jsonSource);

  /**
   * Removes an element by given field name.
   * 
   * @param name
   *          a field name
   * @return true if an element removed, false otherwise
   */
  boolean remove(String name);

}
