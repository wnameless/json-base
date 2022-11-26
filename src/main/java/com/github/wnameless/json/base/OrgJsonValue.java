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
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

/**
 * 
 * The org.json implementation of {@link JsonObjectCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class OrgJsonValue implements JsonValueCore<OrgJsonValue> {

  private final Object jsonValue;

  public OrgJsonValue(Object jsonValue) {
    if (jsonValue == null) throw new NullPointerException();
    if (!(jsonValue instanceof Boolean || jsonValue instanceof String
        || jsonValue instanceof Number || jsonValue instanceof JSONArray
        || jsonValue instanceof JSONObject || jsonValue == JSONObject.NULL)) {
      throw new IllegalArgumentException();
    }
    this.jsonValue = jsonValue;
  }

  public OrgJsonValue(JSONTokener jsonTokener) {
    if (jsonTokener == null) throw new NullPointerException();
    jsonValue = jsonTokener.nextValue();
  }

  @Override
  public boolean isObject() {
    return jsonValue instanceof JSONObject;
  }

  @Override
  public boolean isArray() {
    return jsonValue instanceof JSONArray;
  }

  @Override
  public boolean isString() {
    return jsonValue instanceof String;
  }

  @Override
  public boolean isBoolean() {
    return jsonValue instanceof Boolean;
  }

  @Override
  public boolean isNumber() {
    return jsonValue instanceof Number;
  }

  @Override
  public boolean isNull() {
    return jsonValue == JSONObject.NULL;
  }

  @Override
  public String asString() {
    return (String) jsonValue;
  }

  @Override
  public boolean asBoolean() {
    return (Boolean) jsonValue;
  }

  @Override
  public int asInt() {
    if (jsonValue instanceof Integer) {
      return (Integer) jsonValue;
    } else {
      return Integer.valueOf(jsonValue.toString()).intValue();
    }
  }

  @Override
  public long asLong() {
    if (jsonValue instanceof Long) {
      return (Long) jsonValue;
    } else {
      return Long.valueOf(jsonValue.toString()).longValue();
    }
  }

  @Override
  public BigInteger asBigInteger() {
    return new BigInteger(jsonValue.toString());
  }

  @Override
  public double asDouble() {
    if (jsonValue instanceof Double) {
      return (Double) jsonValue;
    } else {
      return Double.valueOf(jsonValue.toString()).doubleValue();
    }
  }

  @Override
  public BigDecimal asBigDecimal() {
    return new BigDecimal(jsonValue.toString());
  }

  @Override
  public int hashCode() {
    return jsonValue.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrgJsonValue)) return false;
    if (jsonValue instanceof JSONArray
        && ((OrgJsonValue) o).jsonValue instanceof JSONArray) {
      return ((JSONArray) jsonValue).similar(((OrgJsonValue) o).jsonValue);
    }
    if (jsonValue instanceof JSONObject
        && ((OrgJsonValue) o).jsonValue instanceof JSONObject) {
      return ((JSONObject) jsonValue).similar(((OrgJsonValue) o).jsonValue);
    }
    return Objects.equals(jsonValue, ((OrgJsonValue) o).jsonValue);
  }

  @Override
  public String toString() {
    return JSONWriter.valueToString(jsonValue);
  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public Object getSource() {
    return jsonValue;
  }

  @Override
  public OrgJsonObject asObject() {
    return new OrgJsonObject((JSONObject) jsonValue);
  }

  @Override
  public OrgJsonArray asArray() {
    return new OrgJsonArray((JSONArray) jsonValue);
  }

  @Override
  public OrgJsonValue asValue() {
    return this;
  }

}