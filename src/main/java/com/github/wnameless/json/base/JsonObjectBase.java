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

public interface JsonObjectBase<JVB extends JsonValueBase<?>>
    extends Iterable<Entry<String, JVB>>, JsonValueBase<JVB> {

  Iterator<String> names();

  boolean contains(String name);

  JVB get(String name);

  int size();

  default boolean isEmpty() {
    return !iterator().hasNext();
  }

  default Map<String, Object> toMap() {
    return JsonValueUtils.toMap(this);
  }

}
