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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class GsonJsonArray implements JsonArrayCore<GsonJsonValue> {

  private final JsonArray jsonArray;

  public GsonJsonArray(JsonArray jsonArray) {
    this.jsonArray = jsonArray;
  }

  @Override
  public GsonJsonValue get(int index) {
    return new GsonJsonValue(jsonArray.get(index));
  }

  @Override
  public int hashCode() {
    return jsonArray.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GsonJsonArray)) return false;
    return Objects.equals(jsonArray, ((GsonJsonArray) o).jsonArray);
  }

  @Override
  public String toString() {
    return jsonArray.toString();
  }

  @Override
  public Iterator<GsonJsonValue> iterator() {
    return new GsonJsonValueIterator(jsonArray.iterator());
  }

  private class GsonJsonValueIterator implements Iterator<GsonJsonValue> {

    private final Iterator<JsonElement> jsonElementIterator;

    private GsonJsonValueIterator(Iterator<JsonElement> jsonElementIterator) {
      this.jsonElementIterator = jsonElementIterator;
    }

    @Override
    public boolean hasNext() {
      return jsonElementIterator.hasNext();
    }

    @Override
    public GsonJsonValue next() {
      return new GsonJsonValue(jsonElementIterator.next());
    }

  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public void add(JsonValueExtra jsonValue) {
    jsonArray.add((JsonElement) jsonValue.getSource());
  }

  @Override
  public void set(int index, JsonValueExtra jsonValue) {
    jsonArray.set(index, (JsonElement) jsonValue.getSource());
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
  public JsonObjectBase<GsonJsonValue> asObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonArrayBase<GsonJsonValue> asArray() {
    return this;
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
