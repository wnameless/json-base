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
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * 
 * The Jackson 3 implementation of {@link JsonObjectCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class Jackson3JsonObject implements JsonObjectCore<Jackson3JsonValue> {

  private final ObjectNode jsonObject;

  public Jackson3JsonObject(ObjectNode jsonObject) {
    if (jsonObject == null) throw new NullPointerException();
    this.jsonObject = jsonObject;
  }

  @Override
  public void set(String name, JsonSource jsonValue) {
    jsonObject.set(name, (JsonNode) jsonValue.getSource());
  }

  @Override
  public boolean remove(String name) {
    return jsonObject.remove(name) != null;
  }

  @Override
  public boolean contains(String name) {
    return jsonObject.has(name);
  }

  @Override
  public Jackson3JsonValue get(String name) {
    JsonNode node = jsonObject.get(name);
    return node == null ? null : new Jackson3JsonValue(node);
  }

  @Override
  public int size() {
    return jsonObject.size();
  }

  @Override
  public Iterator<String> names() {
    return jsonObject.propertyNames().iterator();
  }

  @Override
  public Iterator<Entry<String, Jackson3JsonValue>> iterator() {
    return new TransformIterator<Entry<String, JsonNode>, Entry<String, Jackson3JsonValue>>(
        jsonObject.properties().iterator(), member -> new SimpleImmutableEntry<>(member.getKey(),
            new Jackson3JsonValue(member.getValue())));
  }

  @Override
  public boolean isObject() {
    return true;
  }

  @Override
  public boolean isArray() {
    return false;
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
    return this;
  }

  @Override
  public Jackson3JsonArray asArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Jackson3JsonValue asValue() {
    return new Jackson3JsonValue(jsonObject);
  }

  @Override
  public Object getSource() {
    return jsonObject;
  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public int hashCode() {
    return jsonObject.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o instanceof Jackson3JsonObject jo && Objects.equals(jsonObject, jo.jsonObject);
  }

  @Override
  public String toString() {
    return jsonObject.toString();
  }

}
