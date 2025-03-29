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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
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
import jakarta.json.Json;

public class JsonArrayCoreTest {

  String str = "text";
  int i = 123;
  long l = 1234567890123456789L;
  double d = 45.67;
  boolean bool = true;
  byte[] bytes = "123".getBytes(StandardCharsets.UTF_8);
  Object obj = null;
  BigInteger bi = new BigInteger("1234567890123456789012345678901234567890");
  BigDecimal bd = new BigDecimal("45.678912367891236789123678912367891236789123");

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
      setBytes(bytes);
      setObj(obj);
    }
  };

  JsonArrayCore<?> gsonAry;
  JsonArrayCore<?> jacksonAry;
  JsonArrayCore<?> orgAry;
  JsonArrayCore<?> jakartaAry;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement = gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonAry = new GsonJsonValue(jsonElement).asObject().get("num").asArray();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonAry = new JacksonJsonValue(jsonNode).asObject().get("num").asArray();

    orgAry = new OrgJsonValue(new JSONObject(jo)).asObject().get("num").asArray();

    jakartaAry = new JakartaJsonArray(
        Json.createArrayBuilder().add(i).add(l).add(d).add(bi).add(bd).build());
  }

  @Test
  public void testConstructorException() {
    assertThrows(NullPointerException.class, () -> {
      new GsonJsonArray(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JacksonJsonArray(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new OrgJsonArray(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JakartaJsonArray(null);
    });
  }

  @Test
  public void testAdd() {
    gsonAry.add(new GsonJsonCore().parse("0"));
    assertEquals(new GsonJsonCore().parse("0"), gsonAry.get(5));

    jacksonAry.add(new JacksonJsonCore().parse("0"));
    assertEquals(new JacksonJsonCore().parse("0"), jacksonAry.get(5));

    orgAry.add(new OrgJsonCore().parse("0"));
    assertEquals(new OrgJsonCore().parse("0"), orgAry.get(5));

    jakartaAry.add(new JakartaJsonCore().parse("0"));
    assertEquals(new JakartaJsonCore().parse("0"), jakartaAry.get(5));
  }

  @Test
  public void testSet() {
    gsonAry.set(4, new GsonJsonCore().parse("0"));
    assertEquals(new GsonJsonCore().parse("0"), gsonAry.get(4));

    jacksonAry.set(4, new JacksonJsonCore().parse("0"));
    assertEquals(new JacksonJsonCore().parse("0"), jacksonAry.get(4));

    orgAry.set(4, new OrgJsonCore().parse("0"));
    assertEquals(new OrgJsonCore().parse("0"), orgAry.get(4));

    jakartaAry.set(4, new JakartaJsonCore().parse("0"));
    assertEquals(new JakartaJsonCore().parse("0"), jakartaAry.get(4));
  }

  @Test
  public void testRemove() {
    assertEquals(new GsonJsonCore().parse("1234567890123456789"), gsonAry.remove(1));
    assertEquals(4, gsonAry.size());

    assertEquals(new JacksonJsonCore().parse("1234567890123456789"), jacksonAry.remove(1));
    assertEquals(4, jacksonAry.size());

    assertEquals(new OrgJsonCore().parse("1234567890123456789"), orgAry.remove(1));
    assertEquals(4, orgAry.size());

    assertEquals(new JakartaJsonCore().parse("1234567890123456789"), jakartaAry.remove(1));
    assertEquals(4, jakartaAry.size());
  }

  @Test
  public void testEquals() {
    assertEquals(new GsonJsonArray((JsonArray) gsonAry.getSource()), gsonAry);
    assertEquals(new JacksonJsonArray((ArrayNode) jacksonAry.getSource()), jacksonAry);
    assertEquals(new OrgJsonArray((JSONArray) orgAry.getSource()), orgAry);
    assertEquals(new JakartaJsonArray((jakarta.json.JsonArray) jakartaAry.getSource()), jakartaAry);

    assertNotEquals(gsonAry, jacksonAry);
    assertNotEquals(gsonAry, orgAry);
    assertNotEquals(gsonAry, jakartaAry);
    assertNotEquals(jacksonAry, orgAry);
    assertNotEquals(jacksonAry, jakartaAry);
    assertNotEquals(orgAry, jakartaAry);
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
    assertEquals(new GsonJsonValue((JsonElement) gsonAry.getSource()), gsonAry.asValue());
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
    assertEquals(new JacksonJsonValue((JsonNode) jacksonAry.getSource()), jacksonAry.asValue());
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

  @Test
  public void testOrgJsonObjectState() {
    assertFalse(orgAry.isObject());
    assertTrue(orgAry.isArray());
    assertFalse(orgAry.isString());
    assertFalse(orgAry.isBoolean());
    assertFalse(orgAry.isNumber());
    assertFalse(orgAry.isNull());

    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asObject();
    });
    assertSame(orgAry, orgAry.asArray());
    assertEquals(new OrgJsonValue((JSONArray) orgAry.getSource()), orgAry.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgAry.asBigDecimal();
    });
    assertTrue(orgAry.getSource() instanceof JSONArray);
  }

  @Test
  public void testJakartaJsonObjectState() {
    assertFalse(jakartaAry.isObject());
    assertTrue(jakartaAry.isArray());
    assertFalse(jakartaAry.isString());
    assertFalse(jakartaAry.isBoolean());
    assertFalse(jakartaAry.isNumber());
    assertFalse(jakartaAry.isNull());

    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asObject();
    });
    assertSame(jakartaAry, jakartaAry.asArray());
    assertEquals(new JakartaJsonValue((jakarta.json.JsonArray) jakartaAry.getSource()),
        jakartaAry.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaAry.asBigDecimal();
    });
    assertTrue(jakartaAry.getSource() instanceof jakarta.json.JsonArray);
  }

}
