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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public final class JacksonJsonArray implements JsonArrayCore<JacksonJsonValue> {

  private final ArrayNode jsonArray;

  public JacksonJsonArray(ArrayNode jsonArray) {
    if (jsonArray == null) throw new NullPointerException();
    this.jsonArray = jsonArray;
  }

  @Override
  public JacksonJsonValue get(int index) {
    return new JacksonJsonValue(jsonArray.get(index));
  }

  @Override
  public int hashCode() {
    return jsonArray.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof JacksonJsonArray)) return false;
    return Objects.equals(jsonArray, ((JacksonJsonArray) o).jsonArray);
  }

  @Override
  public String toString() {
    return jsonArray.toString();
  }

  @Override
  public Iterator<JacksonJsonValue> iterator() {
    return new JacksonJsonValueIterator(jsonArray.iterator());
  }

  private class JacksonJsonValueIterator implements Iterator<JacksonJsonValue> {

    private final Iterator<JsonNode> jsonValueIterator;

    private JacksonJsonValueIterator(Iterator<JsonNode> jsonValueIterator) {
      this.jsonValueIterator = jsonValueIterator;
    }

    @Override
    public boolean hasNext() {
      return jsonValueIterator.hasNext();
    }

    @Override
    public JacksonJsonValue next() {
      return new JacksonJsonValue(jsonValueIterator.next());
    }

  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public void add(JsonValueExtra jsonValue) {
    jsonArray.add((JsonNode) jsonValue.getSource());
  }

  @Override
  public void set(int index, JsonValueExtra jsonValue) {
    jsonArray.set(index, (JsonNode) jsonValue.getSource());
  }

  @Override
  public boolean remove(int index) {
    return jsonArray.remove(index) != null;
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
  public boolean isNumber() {
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
  public boolean isNull() {
    return false;
  }

  @Override
  public JacksonJsonObject asObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public JacksonJsonArray asArray() {
    return this;
  }

  @Override
  public JacksonJsonValue asValue() {
    return new JacksonJsonValue(jsonArray);
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
  public double asDouble() {
    throw new UnsupportedOperationException();
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
  public BigInteger asBigInteger() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BigDecimal asBigDecimal() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getSource() {
    return jsonArray;
  }

  @Override
  public int size() {
    return jsonArray.size();
  }

}
