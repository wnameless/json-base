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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * {@link JsonValueUtils} provides convenient methods to generate Java objects from any JSON wrapper
 * in this library.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class JsonValueUtils {

  private JsonValueUtils() {}

  /**
   * Creates a Java {@link Object} from given {@link JsonValueBase}.
   * 
   * @param jsonValue a {@link JsonValueBase}
   * @return an {@link Object} corresponds to JSON value
   * @throws IllegalStateException if given {@link JsonValueBase} is not represented as any JSON
   *         value
   */
  public static Object toObject(JsonValueBase<?> jsonValue) {
    if (jsonValue.isNull()) return jsonValue.asNull();
    if (jsonValue.isBoolean()) return jsonValue.asBoolean();
    if (jsonValue.isString()) return jsonValue.asString();
    if (jsonValue.isNumber()) return jsonValue.asNumber();
    if (jsonValue.isArray()) return toList(jsonValue.asArray());
    if (jsonValue.isObject()) return toMap(jsonValue.asObject());
    throw new IllegalStateException();
  }

  /**
   * Creates a {@link Number} by given {@link BigDecimal} from {@link Integer}, {@link Long},
   * {@link BigInteger}, {@link Double} and {@link BigDecimal} based on the size and scale of
   * numeric.
   * 
   * @param bd a {@link BigDecimal}
   * @return a {@link Number}
   */
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
    BigDecimal doubleBD = new BigDecimal(String.valueOf(bd.doubleValue()));
    if (doubleBD.compareTo(bd) == 0) {
      if (doubleBD.scale() == bd.scale()) {
        return bd.doubleValue();
      }
    }
    return bd;
  }

  /**
   * Creates a {@link List} by given {@link JsonArrayBase}.
   * 
   * @param jsonArray a {@link JsonArrayBase}
   * @return a {@link List}
   */
  public static List<Object> toList(JsonArrayBase<?> jsonArray) {
    List<Object> list = new ArrayList<>();
    for (JsonValueBase<?> val : jsonArray) {
      list.add(toObject(val));
    }
    return list;
  }

  /**
   * Creates a {@link Map} by given {@link JsonObjectBase}.
   * 
   * @param jsonObject a {@link JsonObjectBase}
   * @return a {@link Map}
   */
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
