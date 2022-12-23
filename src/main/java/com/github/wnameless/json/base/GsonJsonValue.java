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
import java.util.Objects;
import com.google.gson.JsonElement;

/**
 * 
 * The GSON implementation of {@link JsonValueCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class GsonJsonValue implements JsonValueCore<GsonJsonValue> {

  private final JsonElement jsonValue;

  public GsonJsonValue(JsonElement jsonValue) {
    if (jsonValue == null) throw new NullPointerException();
    this.jsonValue = jsonValue;
  }

  @Override
  public boolean isObject() {
    return jsonValue.isJsonObject();
  }

  @Override
  public boolean isArray() {
    return jsonValue.isJsonArray();
  }

  @Override
  public boolean isString() {
    return jsonValue.isJsonPrimitive() ? jsonValue.getAsJsonPrimitive().isString() : false;
  }

  @Override
  public boolean isBoolean() {
    return jsonValue.isJsonPrimitive() ? jsonValue.getAsJsonPrimitive().isBoolean() : false;
  }

  @Override
  public boolean isNumber() {
    return jsonValue.isJsonPrimitive() ? jsonValue.getAsJsonPrimitive().isNumber() : false;
  }

  @Override
  public boolean isNull() {
    return jsonValue.isJsonNull();
  }

  @Override
  public GsonJsonObject asObject() {
    return new GsonJsonObject(jsonValue.getAsJsonObject());
  }

  @Override
  public GsonJsonArray asArray() {
    return new GsonJsonArray(jsonValue.getAsJsonArray());
  }

  @Override
  public GsonJsonValue asValue() {
    return this;
  }

  @Override
  public int asInt() {
    return jsonValue.getAsInt();
  }

  @Override
  public long asLong() {
    return jsonValue.getAsLong();
  }

  @Override
  public BigInteger asBigInteger() {
    return new BigInteger(jsonValue.toString());
  }

  @Override
  public double asDouble() {
    return jsonValue.getAsDouble();
  }

  @Override
  public BigDecimal asBigDecimal() {
    return new BigDecimal(jsonValue.toString());
  }

  @Override
  public String asString() {
    return jsonValue.getAsString();
  }

  @Override
  public boolean asBoolean() {
    return jsonValue.getAsBoolean();
  }

  @Override
  public int hashCode() {
    return jsonValue.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GsonJsonValue)) return false;
    return Objects.equals(jsonValue, ((GsonJsonValue) o).jsonValue);
  }

  @Override
  public String toString() {
    return jsonValue.toString();
  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public JsonElement getSource() {
    return jsonValue;
  }

}
