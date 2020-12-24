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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class JsonArrayCoreTest {

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

  JsonArrayCore<?> gsonAry;
  JsonArrayCore<?> jacksonAry;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonAry = new GsonJsonValue(jsonElement).asObject().get("num").asArray();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonAry = new JacksonJsonValue(jsonNode).asObject().get("num").asArray();
  }

  @Test
  public void testConstrutorException() {
    assertThrows(NullPointerException.class, () -> {
      new GsonJsonArray(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JacksonJsonArray(null);
    });
  }

  @Test
  public void testEquals() {
    assertEquals(new GsonJsonArray((JsonArray) gsonAry.getSource()), gsonAry);
    assertEquals(new JacksonJsonArray((ArrayNode) jacksonAry.getSource()),
        jacksonAry);
    assertNotEquals(gsonAry, jacksonAry);
  }

  @Test
  public void testGsonJsonObjectState() {
    assertFalse(gsonAry.isObject());
    assertTrue(gsonAry.isArray());
    assertFalse(gsonAry.isString());
    assertFalse(gsonAry.isBoolean());
    assertFalse(gsonAry.isNumber());
    assertFalse(gsonAry.isNull());

    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asObject();
    });
    assertSame(gsonAry, gsonAry.asArray());
    assertEquals(new GsonJsonValue((JsonElement) gsonAry.getSource()),
        gsonAry.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      gsonAry.asBigDecimal();
    });
    assertTrue(gsonAry.getSource() instanceof JsonArray);
  }

  @Test
  public void testJacksonJsonObjectState() {
    assertFalse(jacksonAry.isObject());
    assertTrue(jacksonAry.isArray());
    assertFalse(jacksonAry.isString());
    assertFalse(jacksonAry.isBoolean());
    assertFalse(jacksonAry.isNumber());
    assertFalse(jacksonAry.isNull());

    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asObject();
    });
    assertSame(jacksonAry, jacksonAry.asArray());
    assertEquals(new JacksonJsonValue((JsonNode) jacksonAry.getSource()),
        jacksonAry.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jacksonAry.asBigDecimal();
    });
    assertTrue(jacksonAry.getSource() instanceof ArrayNode);
  }

}
