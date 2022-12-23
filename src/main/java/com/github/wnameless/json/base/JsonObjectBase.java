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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 
 * {@link JsonObjectBase} extends {@link JsonValueBase} and adds essential methods which should be
 * included in any JSON object implementation.
 * 
 * @author Wei-Ming Wu
 *
 * @param <JVB> the type of a JSON implementation wrapper
 */
public interface JsonObjectBase<JVB extends JsonValueBase<JVB>>
    extends Iterable<Entry<String, JVB>>, JsonValueBase<JVB> {

  /**
   * Checks if this JSON object contains given field name.
   * 
   * @param name a field name
   * @return true if this JSON object contains given field name, false otherwise
   */
  boolean contains(String name);

  /**
   * Returns a JSON value wrapper by given field name.
   * 
   * @param name a field name
   * @return a JSON value wrapper
   */
  JVB get(String name);

  /**
   * Returns the size of this JSON object.
   * 
   * @return an int
   */
  int size();

  /**
   * Returns field names of this JSON object.
   * 
   * @return an {@link Iterator} of field names
   */
  Iterator<String> names();

  /**
   * Checks if this JSON object is empty.
   * 
   * @return true if this JSON object is empty, false otherwise
   */
  default boolean isEmpty() {
    return !iterator().hasNext();
  }

  /**
   * Converts this JSON object to a Java {@link Map}.
   * 
   * @return a {@link Map}
   */
  default Map<String, Object> toMap() {
    return JsonValueUtils.toMap(this);
  }

  /**
   * Turns this JSON object into a Stream of Entry&lt;String, {@link JsonValueBase}&gt;.
   * 
   * @return a {@link Stream}
   */
  default Stream<Entry<String, JVB>> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

}
