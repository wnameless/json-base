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
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 
 * The GSON implementation of {@link JsonObjectCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class GsonJsonObject implements JsonObjectCore<GsonJsonValue> {

  private final JsonObject jsonObject;

  public GsonJsonObject(JsonObject jsonObject) {
    if (jsonObject == null) throw new NullPointerException();
    this.jsonObject = jsonObject;
  }

  @Override
  public void set(String name, JsonSource jsonValue) {
    jsonObject.add(name, (JsonElement) jsonValue.getSource());
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
  public GsonJsonValue get(String name) {
    JsonElement element = jsonObject.get(name);
    return element == null ? null : new GsonJsonValue(element);
  }

  @Override
  public int size() {
    return jsonObject.size();
  }

  @Override
  public Iterator<String> names() {
    return jsonObject.keySet().iterator();
  }

  @Override
  public Iterator<Entry<String, GsonJsonValue>> iterator() {
    return new TransformIterator<Entry<String, JsonElement>, Entry<String, GsonJsonValue>>(
        jsonObject.entrySet().iterator(), member -> new SimpleImmutableEntry<>(member.getKey(),
            new GsonJsonValue(member.getValue())));
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
  public GsonJsonObject asObject() {
    return this;
  }

  @Override
  public GsonJsonArray asArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public GsonJsonValue asValue() {
    return new GsonJsonValue(jsonObject);
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
    if (!(o instanceof GsonJsonObject)) return false;
    return Objects.equals(jsonObject, ((GsonJsonObject) o).jsonObject);
  }

  @Override
  public String toString() {
    return jsonObject.toString();
  }

}
