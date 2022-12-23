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
import org.json.JSONArray;

/**
 * 
 * The org.json implementation of {@link JsonArrayCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class OrgJsonArray implements JsonArrayCore<OrgJsonValue> {

  private final JSONArray jsonArray;

  public OrgJsonArray(JSONArray jsonArray) {
    if (jsonArray == null) throw new NullPointerException();
    this.jsonArray = jsonArray;
  }

  @Override
  public void add(JsonSource jsonValue) {
    jsonArray.put(jsonValue.getSource());
  }

  @Override
  public void set(int index, JsonSource jsonValue) {
    jsonArray.put(index, jsonValue.getSource());
  }

  @Override
  public OrgJsonValue remove(int index) {
    return new OrgJsonValue(jsonArray.remove(index));
  }

  @Override
  public OrgJsonValue get(int index) {
    return new OrgJsonValue(jsonArray.get(index));
  }

  @Override
  public int size() {
    return jsonArray.length();
  }

  @Override
  public Iterator<OrgJsonValue> iterator() {
    return new TransformIterator<Object, OrgJsonValue>(jsonArray.iterator(), OrgJsonValue::new);
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
  public OrgJsonObject asObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OrgJsonArray asArray() {
    return this;
  }

  @Override
  public OrgJsonValue asValue() {
    return new OrgJsonValue(jsonArray);
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
    if (!(o instanceof OrgJsonArray)) return false;
    return jsonArray.similar(((OrgJsonArray) o).jsonArray);
  }

  @Override
  public String toString() {
    return jsonArray.toString();
  }

}
