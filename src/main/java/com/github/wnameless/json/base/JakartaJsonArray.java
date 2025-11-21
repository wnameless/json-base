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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Objects;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;

/**
 * 
 * The Jakarta implementation of {@link JsonArrayCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class JakartaJsonArray implements JsonArrayCore<JakartaJsonValue> {

  private JsonArray jsonArray;

  public JakartaJsonArray(JsonArray jsonArray) {
    if (jsonArray == null) throw new NullPointerException();
    this.jsonArray = jsonArray;
  }

  @Override
  public void add(JsonSource jsonValue) {
    jsonArray = Json.createArrayBuilder(jsonArray).add((JsonValue) jsonValue.getSource()).build();
  }

  @Override
  public void set(int index, JsonSource jsonValue) {
    jsonArray =
        Json.createArrayBuilder(jsonArray).set(index, (JsonValue) jsonValue.getSource()).build();
  }

  @Override
  public JakartaJsonValue remove(int index) {
    JakartaJsonValue value = new JakartaJsonValue(jsonArray.get(index));
    jsonArray = Json.createArrayBuilder(jsonArray).remove(index).build();
    return value;
  }

  @Override
  public JakartaJsonValue get(int index) {
    return new JakartaJsonValue(jsonArray.get(index));
  }

  @Override
  public int size() {
    return jsonArray.size();
  }

  @Override
  public Iterator<JakartaJsonValue> iterator() {
    return new TransformIterator<JsonValue, JakartaJsonValue>(jsonArray.iterator(),
        JakartaJsonValue::new);
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
  public JakartaJsonObject asObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public JakartaJsonArray asArray() {
    return this;
  }

  @Override
  public JakartaJsonValue asValue() {
    return new JakartaJsonValue(jsonArray);
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
    return o instanceof JakartaJsonArray ja && Objects.equals(jsonArray, ja.jsonArray);
  }

  @Override
  public String toString() {
    return jsonArray.toString();
  }

}
