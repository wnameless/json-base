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
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

/**
 * 
 * The Jakarta implementation of {@link JsonObjectCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class JakartaJsonObject
    implements JsonObjectCore<JakartaJsonValue> {

  private final JsonObject jsonObject;

  public JakartaJsonObject(JsonObject jsonObject) {
    if (jsonObject == null) throw new NullPointerException();
    this.jsonObject = jsonObject;
  }

  @Override
  public Iterator<String> names() {
    return jsonObject.keySet().iterator();
  }

  @Override
  public boolean contains(String name) {
    return jsonObject.containsKey(name);
  }

  @Override
  public JakartaJsonValue get(String name) {
    return new JakartaJsonValue(jsonObject.get(name));
  }

  @Override
  public int size() {
    return jsonObject.size();
  }

  @Override
  public Iterator<Entry<String, JakartaJsonValue>> iterator() {
    return new JakartaJsonEntryIterator(jsonObject.entrySet().iterator());
  }

  private final class JakartaJsonEntryIterator
      implements Iterator<Entry<String, JakartaJsonValue>> {

    private final Iterator<Entry<String, JsonValue>> jsonValueIterator;

    private JakartaJsonEntryIterator(
        Iterator<Entry<String, JsonValue>> jsonValueIterator) {
      this.jsonValueIterator = jsonValueIterator;
    }

    @Override
    public boolean hasNext() {
      return jsonValueIterator.hasNext();
    }

    @Override
    public Entry<String, JakartaJsonValue> next() {
      Entry<String, JsonValue> member = jsonValueIterator.next();
      return new AbstractMap.SimpleImmutableEntry<>(member.getKey(),
          new JakartaJsonValue(member.getValue()));
    }

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
  public String toJson() {
    return toString();
  }

  @Override
  public JakartaJsonObject asObject() {
    return this;
  }

  @Override
  public JakartaJsonArray asArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public JakartaJsonValue asValue() {
    return new JakartaJsonValue(jsonObject);
  }

  @Override
  public Object getSource() {
    return jsonObject;
  }

  @Override
  public void set(String name, JsonSource jsonValue) {
    jsonObject.put(name, (JsonValue) jsonValue.getSource());
  }

  @Override
  public boolean remove(String name) {
    return jsonObject.remove(name) != null;
  }

  @Override
  public int hashCode() {
    return jsonObject.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof JakartaJsonObject)) return false;
    return Objects.equals(jsonObject, ((JakartaJsonObject) o).jsonObject);
  }

  @Override
  public String toString() {
    return jsonObject.toString();
  }

}
