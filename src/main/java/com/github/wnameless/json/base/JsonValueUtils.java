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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JsonValueUtils {

  private JsonValueUtils() {}

  public static Object toObject(JsonValueBase<?> jsonValue) {
    if (jsonValue.isNull()) return jsonValue.asNull();
    if (jsonValue.isBoolean()) return jsonValue.asBoolean();
    if (jsonValue.isString()) return jsonValue.asString();
    if (jsonValue.isNumber()) return jsonValue.asNumber();
    if (jsonValue.isArray()) return toList(jsonValue.asArray());
    if (jsonValue.isObject()) return toMap(jsonValue.asObject());
    throw new IllegalStateException();
  }

  public static Number toJavaNumber(BigDecimal bd) {
    if (BigDecimal.valueOf(bd.intValue()).equals(bd)) {
      return bd.intValue();
    }
    if (BigDecimal.valueOf(bd.longValue()).equals(bd)) {
      return bd.longValue();
    }
    if (bd.scale() <= 0) {
      return bd.toBigInteger();
    }
    if (BigDecimal.valueOf(bd.doubleValue()).compareTo(bd) == 0) {
      return bd.doubleValue();
    }
    return bd;
  }

  public static List<Object> toList(JsonArrayBase<?> jsonArray) {
    List<Object> list = new ArrayList<>();
    for (JsonValueBase<?> val : jsonArray) {
      list.add(toObject(val));
    }
    return list;
  }

  public static Map<String, Object> toMap(JsonObjectBase<?> jsonObject) {
    Map<String, Object> map = new LinkedHashMap<>();
    Iterator<String> names = jsonObject.names();
    while (names.hasNext()) {
      String name = names.next();
      JsonValueBase<?> val = jsonObject.get(name);
      map.put(name, toObject(val));
    }
    return map;
  }

}
