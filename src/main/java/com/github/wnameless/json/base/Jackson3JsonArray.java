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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Objects;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;

/**
 * 
 * The Jackson 3 implementation of {@link JsonArrayCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class Jackson3JsonArray implements JsonArrayCore<Jackson3JsonValue> {

  private final ArrayNode jsonArray;

  public Jackson3JsonArray(ArrayNode jsonArray) {
    if (jsonArray == null) throw new NullPointerException();
    this.jsonArray = jsonArray;
  }

  @Override
  public void add(JsonSource jsonValue) {
    jsonArray.add((JsonNode) jsonValue.getSource());
  }

  @Override
  public void set(int index, JsonSource jsonValue) {
    jsonArray.set(index, (JsonNode) jsonValue.getSource());
  }

  @Override
  public Jackson3JsonValue remove(int index) {
    return new Jackson3JsonValue(jsonArray.remove(index));
  }

  @Override
  public Jackson3JsonValue get(int index) {
    return new Jackson3JsonValue(jsonArray.get(index));
  }

  @Override
  public int size() {
    return jsonArray.size();
  }

  @Override
  public Iterator<Jackson3JsonValue> iterator() {
    return new TransformIterator<JsonNode, Jackson3JsonValue>(jsonArray.iterator(),
        Jackson3JsonValue::new);
  }

  @Override
  public boolean isObject() {
    return false;
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public boolean isString() {
    return false;
  }

  @Override
  public boolean isBoolean() {
    return false;
  }

  @Override
  public boolean isNumber() {
    return false;
  }

  @Override
  public boolean isNull() {
    return false;
  }

  @Override
  public String asString() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean asBoolean() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int asInt() {
    throw new UnsupportedOperationException();
  }

  @Override
  public long asLong() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BigInteger asBigInteger() {
    throw new UnsupportedOperationException();
  }

  @Override
  public double asDouble() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BigDecimal asBigDecimal() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Jackson3JsonObject asObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Jackson3JsonArray asArray() {
    return this;
  }

  @Override
  public Jackson3JsonValue asValue() {
    return new Jackson3JsonValue(jsonArray);
  }

  @Override
  public Object getSource() {
    return jsonArray;
  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public int hashCode() {
    return jsonArray.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o instanceof Jackson3JsonArray ja && Objects.equals(jsonArray, ja.jsonArray);
  }

  @Override
  public String toString() {
    return jsonArray.toString();
  }

}
