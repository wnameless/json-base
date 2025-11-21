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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * 
 * The Jackson 3 implementation of {@link JsonValueCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class Jackson3JsonValue implements JsonValueCore<Jackson3JsonValue> {

  private final JsonNode jsonValue;

  public Jackson3JsonValue(JsonNode jsonValue) {
    if (jsonValue == null) throw new NullPointerException();
    this.jsonValue = jsonValue;
  }

  @Override
  public boolean isObject() {
    return jsonValue.isObject();
  }

  @Override
  public boolean isArray() {
    return jsonValue.isArray();
  }

  @Override
  public boolean isString() {
    return jsonValue.isString() || jsonValue.isBinary() || jsonValue.isEmbeddedValue();
  }

  @Override
  public boolean isBoolean() {
    return jsonValue.isBoolean();
  }

  @Override
  public boolean isNumber() {
    return jsonValue.isNumber();
  }

  @Override
  public boolean isNull() {
    return jsonValue.isNull();
  }

  @Override
  public Jackson3JsonObject asObject() {
    return new Jackson3JsonObject((ObjectNode) jsonValue);
  }

  @Override
  public Jackson3JsonArray asArray() {
    return new Jackson3JsonArray((ArrayNode) jsonValue);
  }

  @Override
  public Jackson3JsonValue asValue() {
    return this;
  }

  @Override
  public String asString() {
    if (jsonValue.isEmbeddedValue()) {
      return new String(Base64.getEncoder().encode(jsonValue.binaryValue()),
          StandardCharsets.UTF_8);
    }
    return jsonValue.asString();
  }

  @Override
  public boolean asBoolean() {
    return jsonValue.asBoolean();
  }

  @Override
  public int asInt() {
    return jsonValue.asInt();
  }

  @Override
  public long asLong() {
    return jsonValue.asLong();
  }

  @Override
  public BigInteger asBigInteger() {
    return jsonValue.bigIntegerValue();
  }

  @Override
  public double asDouble() {
    return jsonValue.asDouble();
  }

  @Override
  public BigDecimal asBigDecimal() {
    return jsonValue.decimalValue();
  }

  @Override
  public int hashCode() {
    return jsonValue.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o instanceof Jackson3JsonValue jv && Objects.equals(jsonValue, jv.jsonValue);
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
  public JsonNode getSource() {
    return jsonValue;
  }

}
