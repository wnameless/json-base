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

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.EqualsTester;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class JsonValueBaseTest {

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

  JsonValueBase<?> jsonValue;

  @Test
  public void testConstructorException() {
    assertThrows(NullPointerException.class, () -> {
      new GsonJsonValue(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JacksonJsonValue(null);
    });
  }

  @Test
  public void testGsonValue() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    jsonValue = new GsonJsonValue(jsonElement);

    assertTrue(jsonValue.isObject());
    assertTrue(jsonValue.asObject().get("str").isString());
    assertEquals(str, jsonValue.asObject().get("str").asString());
    assertTrue(jsonValue.asObject().get("num").isArray());
    assertTrue(jsonValue.asObject().get("num").asArray().get(0).isNumber());
    assertEquals(i, jsonValue.asObject().get("num").asArray().get(0).asInt());
    assertEquals(i,
        jsonValue.asObject().get("num").asArray().get(0).asNumber());
    assertTrue(jsonValue.asObject().get("num").asArray().get(1).isNumber());
    assertEquals(l, jsonValue.asObject().get("num").asArray().get(1).asLong());
    assertEquals(l,
        jsonValue.asObject().get("num").asArray().get(1).asNumber());
    assertTrue(jsonValue.asObject().get("num").asArray().get(2).isNumber());
    assertEquals(d, jsonValue.asObject().get("num").asArray().get(2).asDouble(),
        0.0);
    assertEquals(d,
        jsonValue.asObject().get("num").asArray().get(2).asNumber());
    assertEquals(bi,
        jsonValue.asObject().get("num").asArray().get(3).asBigInteger());
    assertEquals(bi,
        jsonValue.asObject().get("num").asArray().get(3).asNumber());
    assertEquals(bd,
        jsonValue.asObject().get("num").asArray().get(4).asBigDecimal());
    assertEquals(bd,
        jsonValue.asObject().get("num").asArray().get(4).asNumber());
    assertTrue(jsonValue.asObject().get("bool").isBoolean());
    assertTrue(jsonValue.asObject().get("bool").asBoolean());
    assertTrue(jsonValue.asObject().get("obj").isNull());
    assertSame(null, jsonValue.asObject().get("obj").asNull());

    assertSame(jsonValue, jsonValue.asValue());

    new EqualsTester().addEqualityGroup(jsonValue).testEquals();
    new EqualsTester().addEqualityGroup(jsonValue.asObject()).testEquals();
    new EqualsTester()
        .addEqualityGroup(jsonValue.asObject().get("num").asArray())
        .testEquals();
  }

  @Test
  public void testGsonValueToJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    GsonJsonValue jsonValue = new GsonJsonValue(jsonElement);
    assertEquals(
        "{\"str\":\"text\",\"num\":[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123],\"bool\":true,\"obj\":null}",
        jsonValue.toJson());
    assertEquals(
        "{\"str\":\"text\",\"num\":[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123],\"bool\":true,\"obj\":null}",
        jsonValue.asObject().toJson());
    assertEquals(
        "[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123]",
        jsonValue.asObject().get("num").asArray().toJson());
  }

  @Test
  public void testJacksonValue() {
    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jsonValue = new JacksonJsonValue(jsonNode);

    assertTrue(jsonValue.isObject());
    assertTrue(jsonValue.asObject().get("str").isString());
    assertEquals(str, jsonValue.asObject().get("str").asString());
    assertTrue(jsonValue.asObject().get("num").isArray());
    assertTrue(jsonValue.asObject().get("num").asArray().get(0).isNumber());
    assertEquals(i, jsonValue.asObject().get("num").asArray().get(0).asInt());
    assertEquals(i,
        jsonValue.asObject().get("num").asArray().get(0).asNumber());
    assertTrue(jsonValue.asObject().get("num").asArray().get(1).isNumber());
    assertEquals(l, jsonValue.asObject().get("num").asArray().get(1).asLong());
    assertEquals(l,
        jsonValue.asObject().get("num").asArray().get(1).asNumber());
    assertTrue(jsonValue.asObject().get("num").asArray().get(2).isNumber());
    assertEquals(d, jsonValue.asObject().get("num").asArray().get(2).asDouble(),
        0.0);
    assertEquals(d,
        jsonValue.asObject().get("num").asArray().get(2).asNumber());
    assertEquals(bi,
        jsonValue.asObject().get("num").asArray().get(3).asBigInteger());
    assertEquals(bi,
        jsonValue.asObject().get("num").asArray().get(3).asNumber());
    assertEquals(bd,
        jsonValue.asObject().get("num").asArray().get(4).asBigDecimal());
    assertEquals(bd,
        jsonValue.asObject().get("num").asArray().get(4).asNumber());
    assertTrue(jsonValue.asObject().get("bool").isBoolean());
    assertTrue(jsonValue.asObject().get("bool").asBoolean());
    assertTrue(jsonValue.asObject().get("obj").isNull());
    assertSame(null, jsonValue.asObject().get("obj").asNull());

    assertSame(jsonValue, jsonValue.asValue());

    new EqualsTester().addEqualityGroup(jsonValue).testEquals();
    new EqualsTester().addEqualityGroup(jsonValue.asObject()).testEquals();
    new EqualsTester()
        .addEqualityGroup(jsonValue.asObject().get("num").asArray())
        .testEquals();
  }

  @Test
  public void testJacksonValueToJson() {
    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    JacksonJsonValue jsonValue = new JacksonJsonValue(jsonNode);
    assertEquals(
        "{\"str\":\"text\",\"num\":[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123],\"bool\":true,\"obj\":null}",
        jsonValue.toJson());
    assertEquals(
        "{\"str\":\"text\",\"num\":[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123],\"bool\":true,\"obj\":null}",
        jsonValue.asObject().toJson());
    assertEquals(
        "[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123]",
        jsonValue.asObject().get("num").asArray().toJson());
  }

  @Test
  public void testGsonArrayIterable() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    GsonJsonValue gsonJson = new GsonJsonValue(jsonElement);

    JsonArrayBase<GsonJsonValue> array =
        gsonJson.asObject().get("num").asArray();
    Iterator<GsonJsonValue> iter = array.iterator();

    assertEquals(array.get(0), iter.next());
    assertEquals(array.get(1), iter.next());
    assertEquals(array.get(2), iter.next());
    assertEquals(array.get(3), iter.next());
    assertEquals(array.get(4), iter.next());
    assertFalse(iter.hasNext());

    assertFalse(array.isEmpty());
    jsonElement = gson.toJsonTree(new ArrayList<>(),
        new TypeToken<ArrayList<?>>() {}.getType());
    gsonJson = new GsonJsonValue(jsonElement);
    array = gsonJson.asArray();
    assertTrue(array.isEmpty());
  }

  @Test
  public void testGsonObjectIterable() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    GsonJsonValue jsonValue = new GsonJsonValue(jsonElement);
    GsonJsonObject gsonObject = jsonValue.asObject();

    Iterator<Entry<String, GsonJsonValue>> iter = gsonObject.iterator();

    Entry<String, GsonJsonValue> element = iter.next();
    assertEquals("str", element.getKey());
    assertEquals(gsonObject.get("str"), element.getValue());

    element = iter.next();
    assertEquals("num", element.getKey());
    assertEquals(gsonObject.get("num"), element.getValue());

    element = iter.next();
    assertEquals("bool", element.getKey());
    assertEquals(gsonObject.get("bool"), element.getValue());

    element = iter.next();
    assertEquals("obj", element.getKey());
    assertEquals(gsonObject.get("obj"), element.getValue());

    assertFalse(iter.hasNext());

    assertFalse(gsonObject.isEmpty());
    jsonElement =
        gson.toJsonTree(new Object(), new TypeToken<Object>() {}.getType());
    jsonValue = new GsonJsonValue(jsonElement);
    gsonObject = jsonValue.asObject();
    assertTrue(gsonObject.isEmpty());
  }

  @Test
  public void testJacksonArrayIterable() {
    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    JacksonJsonValue jacksonJson = new JacksonJsonValue(jsonNode);

    JsonArrayBase<JacksonJsonValue> array =
        jacksonJson.asObject().get("num").asArray();
    Iterator<JacksonJsonValue> iter = array.iterator();

    assertEquals(array.get(0), iter.next());
    assertEquals(array.get(1), iter.next());
    assertEquals(array.get(2), iter.next());
    assertEquals(array.get(3), iter.next());
    assertEquals(array.get(4), iter.next());
    assertFalse(iter.hasNext());

    assertFalse(array.isEmpty());
    jsonNode = new ObjectMapper().valueToTree(new ArrayList<>());
    jacksonJson = new JacksonJsonValue(jsonNode);
    array = jacksonJson.asArray();
    assertTrue(array.isEmpty());
  }

  @Test
  public void testJacksonObjectIterable() {
    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    JacksonJsonValue jacksonJson = new JacksonJsonValue(jsonNode);
    JacksonJsonObject jacksonObject = jacksonJson.asObject();

    Iterator<Entry<String, JacksonJsonValue>> iter = jacksonObject.iterator();

    Entry<String, JacksonJsonValue> element = iter.next();
    assertEquals("str", element.getKey());
    assertEquals(jacksonObject.get("str"), element.getValue());

    element = iter.next();
    assertEquals("num", element.getKey());
    assertEquals(jacksonObject.get("num"), element.getValue());

    element = iter.next();
    assertEquals("bool", element.getKey());
    assertEquals(jacksonObject.get("bool"), element.getValue());

    element = iter.next();
    assertEquals("obj", element.getKey());
    assertEquals(jacksonObject.get("obj"), element.getValue());

    assertFalse(iter.hasNext());

    assertFalse(jacksonObject.isEmpty());
    jsonNode = new ObjectMapper().valueToTree(new HashMap<>());
    jacksonJson = new JacksonJsonValue(jsonNode);
    jacksonObject = jacksonJson.asObject();
    assertTrue(jacksonObject.isEmpty());
  }

}
