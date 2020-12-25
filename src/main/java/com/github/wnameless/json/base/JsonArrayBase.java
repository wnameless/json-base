/*
 *
 * Copyright 2019 Wei-Ming Wu
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

import java.util.List;

/**
 * 
 * {@link JsonArrayBase} extends {@link JsonValueBase} and adds essential
 * methods which should be included in any JSON array implementation.
 * 
 * @author Wei-Ming Wu
 *
 * @param <JVB>
 *          the type of a JSON implementation wrapper
 */
public interface JsonArrayBase<JVB extends JsonValueBase<JVB>>
    extends Iterable<JVB>, JsonValueBase<JVB> {

  /**
   * Returns a JSON value wrapper by given index.
   * 
   * @param index
   *          a position in this JSON array
   * @return a JSON value wrapper
   */
  JVB get(int index);

  /**
   * Returns the size of this JSON array.
   * 
   * @return an int
   */
  int size();

  /**
   * Checks if this JSON array is empty.
   * 
   * @return true if this JSON array is empty, false otherwise
   */
  default boolean isEmpty() {
    return !iterator().hasNext();
  }

  /**
   * Converts this JSON array to a Java {@link List}.
   * 
   * @return a {@link List}
   */
  default List<Object> toList() {
    return JsonValueUtils.toList(this);
  }

}
