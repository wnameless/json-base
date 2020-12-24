/*
 *
 * Copyright 2020 Wei-Ming Wu
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

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JsonObjectCoreTest {

  String str = "text";
  int i = 123;
  long l = 1234567890123456789L;
  double d = 45.67;
  boolean bool = true;
  Object obj = null;
  BigInteger bi = new BigInteger("1234567890123456789012345678901234567890");
  BigDecimal bd =
      new BigDecimal("45.678912367891236789123678912367891236789123");

  JsonPOJO jo = new JsonPOJO() {
    {
      setStr(str);
      setNum(new ArrayList<Number>() {
        private static final long serialVersionUID = 1L;
        {
          add(i);
          add(l);
          add(d);
          add(bi);
          add(bd);
        }
      });
      setBool(bool);
      setObj(obj);
    }
  };

  JsonObjectCore<?> gsonObj;
  JsonObjectCore<?> jacksonObj;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonObj = new GsonJsonValue(jsonElement).asObject();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonObj = new JacksonJsonValue(jsonNode).asObject();
  }

  @Test
  public void testConstrutorException() {
    assertThrows(NullPointerException.class, () -> {
      new GsonJsonObject(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JacksonJsonObject(null);
    });
  }

  @Test
  public void testEquals() {
    assertEquals(new GsonJsonObject((JsonObject) gsonObj.getSource()), gsonObj);
    assertEquals(new JacksonJsonObject((ObjectNode) jacksonObj.getSource()),
        jacksonObj);
    assertNotEquals(gsonObj, jacksonObj);
  }

  @Test
  public void testGsonJsonObjectState() {
    assertTrue(gsonObj.isObject());
    assertFalse(gsonObj.isArray());
    assertFalse(gsonObj.isString());
    assertFalse(gsonObj.isBoolean());
    assertFalse(gsonObj.isNumber());
    assertFalse(gsonObj.isNull());

    assertSame(gsonObj, gsonObj.asObject());
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asArray();
    });
    assertEquals(new GsonJsonValue((JsonElement) gsonObj.getSource()),
        gsonObj.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonObj.asBigDecimal();
    });
    assertTrue(gsonObj.getSource() instanceof JsonObject);
  }

  @Test
  public void testJacksonJsonObjectState() {
    assertTrue(jacksonObj.isObject());
    assertFalse(jacksonObj.isArray());
    assertFalse(jacksonObj.isString());
    assertFalse(jacksonObj.isBoolean());
    assertFalse(jacksonObj.isNumber());
    assertFalse(jacksonObj.isNull());

    assertSame(jacksonObj, jacksonObj.asObject());
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asArray();
    });
    assertEquals(new JacksonJsonValue((JsonNode) jacksonObj.getSource()),
        jacksonObj.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonObj.asBigDecimal();
    });
    assertTrue(jacksonObj.getSource() instanceof ObjectNode);
  }

}
