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
import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

/**
 * 
 * The Jakarta implementation of {@link JsonValueCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class JakartaJsonValue implements JsonValueCore<JakartaJsonValue> {

  private final JsonValue jsonValue;

  public JakartaJsonValue(JsonValue jsonValue) {
    if (jsonValue == null) throw new NullPointerException();
    this.jsonValue = jsonValue;
  }

  @Override
  public boolean isObject() {
    return jsonValue.getValueType() == JsonValue.ValueType.OBJECT;
  }

  @Override
  public boolean isArray() {
    return jsonValue.getValueType() == JsonValue.ValueType.ARRAY;
  }

  @Override
  public boolean isString() {
    return jsonValue.getValueType() == JsonValue.ValueType.STRING;
  }

  @Override
  public boolean isBoolean() {
    return jsonValue.getValueType() == JsonValue.ValueType.FALSE
        || jsonValue.getValueType() == JsonValue.ValueType.TRUE;
  }

  @Override
  public boolean isNumber() {
    return jsonValue.getValueType() == JsonValue.ValueType.NUMBER;
  }

  @Override
  public boolean isNull() {
    return jsonValue.getValueType() == JsonValue.ValueType.NULL;
  }

  @Override
  public String asString() {
    return JsonString.class.cast(jsonValue).getString();
  }

  @Override
  public boolean asBoolean() {
    if (jsonValue.getValueType() == JsonValue.ValueType.FALSE) return false;
    if (jsonValue.getValueType() == JsonValue.ValueType.TRUE) return true;
    throw new UnsupportedOperationException();
  }

  @Override
  public int asInt() {
    return JsonNumber.class.cast(jsonValue).intValue();
  }

  @Override
  public long asLong() {
    return JsonNumber.class.cast(jsonValue).longValue();
  }

  @Override
  public BigInteger asBigInteger() {
    return JsonNumber.class.cast(jsonValue).bigIntegerValue();
  }

  @Override
  public double asDouble() {
    return JsonNumber.class.cast(jsonValue).doubleValue();
  }

  @Override
  public BigDecimal asBigDecimal() {
    return JsonNumber.class.cast(jsonValue).bigDecimalValue();
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
  public JakartaJsonObject asObject() {
    return new JakartaJsonObject(jsonValue.asJsonObject());
  }

  @Override
  public JakartaJsonArray asArray() {
    return new JakartaJsonArray(jsonValue.asJsonArray());
  }

  @Override
  public JakartaJsonValue asValue() {
    return this;
  }

  @Override
  public int hashCode() {
    return jsonValue.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof JakartaJsonValue)) return false;
    return Objects.equals(jsonValue, ((JakartaJsonValue) o).jsonValue);
  }

  @Override
  public String toString() {
    return jsonValue.toString();
  }

}
