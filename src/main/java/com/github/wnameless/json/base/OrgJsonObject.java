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
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;

/**
 * 
 * The org.json implementation of {@link JsonObjectCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class OrgJsonObject implements JsonObjectCore<OrgJsonValue> {

  private final JSONObject jsonObject;

  public OrgJsonObject(JSONObject jsonObject) {
    if (jsonObject == null) throw new NullPointerException();
    this.jsonObject = jsonObject;
  }

  @Override
  public void set(String name, JsonSource jsonValue) {
    jsonObject.put(name, jsonValue.getSource());
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
  public OrgJsonValue get(String name) {
    return contains(name) ? new OrgJsonValue(jsonObject.get(name)) : null;
  }

  @Override
  public int size() {
    return jsonObject.length();
  }

  @Override
  public Iterator<String> names() {
    return jsonObject.keys();
  }

  @Override
  public Iterator<Entry<String, OrgJsonValue>> iterator() {
    return new TransformIterator<String, Entry<String, OrgJsonValue>>(jsonObject.keys(),
        key -> new SimpleImmutableEntry<>(key, new OrgJsonValue(jsonObject.get(key))));
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
  public OrgJsonObject asObject() {
    return this;
  }

  @Override
  public OrgJsonArray asArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OrgJsonValue asValue() {
    return new OrgJsonValue(jsonObject);
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
    if (!(o instanceof OrgJsonObject)) return false;
    return jsonObject.similar(((OrgJsonObject) o).jsonObject);
  }

  @Override
  public String toString() {
    return jsonObject.toString();
  }

}
