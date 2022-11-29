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

/**
 * 
 * {@link JsonValueBase} defines all essential methods which should be included
 * in any JSON implementation object. This interface should be used as a wrapper
 * to a JSON value implementation.
 * 
 * @author Wei-Ming Wu
 *
 * @param <JVB>
 *          the type of a JSON implementation wrapper
 */
public interface JsonValueBase<JVB extends JsonValueBase<JVB>>
    extends Jsonable {

  /**
   * Checks if this is represented as a JSON object.
   * 
   * @return true if this is represented as a JSON object, false otherwise
   */
  public boolean isObject();

  /**
   * Checks if this is represented as a JSON array.
   * 
   * @return true if this is represented as a JSON array, false otherwise
   */
  public boolean isArray();

  /**
   * Checks if this is represented as a JSON string.
   * 
   * @return true if this is represented as a JSON string, false otherwise
   */
  public boolean isString();

  /**
   * Checks if this is represented as a JSON boolean.
   * 
   * @return true if this is represented as a JSON boolean, false otherwise
   */
  public boolean isBoolean();

  /**
   * Checks if this is represented as a JSON number.
   * 
   * @return true if this is represented as a JSON number, false otherwise
   */
  public boolean isNumber();

  /**
   * Checks if this is represented as a JSON null.
   * 
   * @return true if this is represented as a JSON null, false otherwise
   */
  public boolean isNull();

  /**
   * Converts this to a Java {@link String}.
   * 
   * @return a {@link String}
   */
  public String asString();

  /**
   * Converts this to a Java boolean.
   * 
   * @return a boolean
   */
  public boolean asBoolean();

  /**
   * Converts this to a Java int.
   * 
   * @return an int
   */
  public int asInt();

  /**
   * Converts this to a Java long.
   * 
   * @return a long
   */
  public long asLong();

  /**
   * Converts this to a Java {@link BigInteger}.
   * 
   * @return a {@link BigInteger}
   */
  public BigInteger asBigInteger();

  /**
   * Converts this to a Java double.
   * 
   * @return a double
   */
  public double asDouble();

  /**
   * Converts this to a Java {@link BigDecimal}.
   * 
   * @return a {@link BigDecimal}
   */
  public BigDecimal asBigDecimal();

  /**
   * Converts this to a Java {@link Number}.<br>
   * <br>
   * Converted {@link Number} is chosen from {@link Integer}, {@link Long},
   * {@link BigInteger}, {@link Double} and {@link BigDecimal} based on the size
   * and scale of numeric.
   * 
   * @return a {@link Number}
   */
  default Number asNumber() {
    return JsonValueUtils.toJavaNumber(asBigDecimal());
  }

  /**
   * Converts this to a Java null.
   * 
   * @return a null
   */
  default Object asNull() {
    return null;
  }

  /**
   * Converts this to a JSON object wrapper.
   * 
   * @return a JSON object wrapper
   */
  public JsonObjectBase<JVB> asObject();

  /**
   * Converts this to a JSON array wrapper.
   * 
   * @return a JSON array wrapper
   */
  public JsonArrayBase<JVB> asArray();

  /**
   * Converts this to a JSON value wrapper.
   * 
   * @return a JSON value wrapper
   */
  public JsonValueBase<JVB> asValue();

}
