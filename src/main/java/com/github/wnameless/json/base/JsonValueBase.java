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

public interface JsonValueBase<JVB extends JsonValueBase<?>> extends Jsonable {

  public boolean isObject();

  public boolean isArray();

  public boolean isNumber();

  public boolean isString();

  public boolean isBoolean();

  public boolean isNull();

  public JsonObjectBase<JVB> asObject();

  public JsonArrayBase<JVB> asArray();

  public JsonValueBase<JVB> asValue();

  public int asInt();

  public long asLong();

  public double asDouble();

  public String asString();

  public boolean asBoolean();

  public BigInteger asBigInteger();

  public BigDecimal asBigDecimal();

}
