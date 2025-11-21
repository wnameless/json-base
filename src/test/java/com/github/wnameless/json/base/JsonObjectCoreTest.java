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
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONObject;
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
import jakarta.json.Json;
import jakarta.json.JsonValue;

public class JsonObjectCoreTest {

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

  JsonObjectCore<?> gsonObj;
  JsonObjectCore<?> jacksonObj;
  JsonObjectCore<?> jackson3Obj;
  JsonObjectCore<?> orgObj;
  JsonObjectCore<?> jakartaObj;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement = gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonObj = new GsonJsonValue(jsonElement).asObject();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonObj = new JacksonJsonValue(jsonNode).asObject();

    tools.jackson.databind.JsonNode j3JsonNode =
        new tools.jackson.databind.ObjectMapper().valueToTree(jo);
    jackson3Obj = new Jackson3JsonValue(j3JsonNode).asObject();

    jo.setObj(JSONObject.NULL);
    orgObj = new OrgJsonValue(new JSONObject(jo)).asObject();

    jakartaObj = new JakartaJsonValue(Json.createObjectBuilder().add("str", str)
        .add("num", Json.createArrayBuilder().add(i).add(l).add(d).add(bi).add(bd).build())
        .add("bool", bool).add("obj", JsonValue.NULL).build()).asObject();
  }

  @Test
  public void testConstructorException() {
    assertThrows(NullPointerException.class, () -> {
      new GsonJsonObject(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JacksonJsonObject(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new Jackson3JsonObject(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new OrgJsonObject(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JakartaJsonObject(null);
    });
  }

  @Test
  public void testSetRemove() {
    assertFalse(gsonObj.contains("text"));
    assertFalse(jacksonObj.contains("text"));
    assertFalse(jackson3Obj.contains("text"));
    assertFalse(orgObj.contains("text"));
    assertFalse(jakartaObj.contains("text"));

    gsonObj.set("text", new GsonJsonCore().parse("\"str\""));
    jacksonObj.set("text", new JacksonJsonCore().parse("\"str\""));
    jackson3Obj.set("text", new Jackson3JsonCore().parse("\"str\""));
    orgObj.set("text", new OrgJsonCore().parse("\"str\""));
    jakartaObj.set("text", new JakartaJsonCore().parse("\"str\""));

    assertEquals(6, gsonObj.size());
    assertEquals(6, jacksonObj.size());
    assertEquals(6, jackson3Obj.size());
    assertEquals(6, orgObj.size());
    assertEquals(5, jakartaObj.size());

    assertEquals("str", gsonObj.get("text").asString());
    assertEquals("str", jacksonObj.get("text").asString());
    assertEquals("str", jackson3Obj.get("text").asString());
    assertEquals("str", orgObj.get("text").asString());
    assertEquals("str", jakartaObj.get("text").asString());

    assertTrue(gsonObj.contains("text"));
    assertTrue(jacksonObj.contains("text"));
    assertTrue(jackson3Obj.contains("text"));
    assertTrue(orgObj.contains("text"));
    assertTrue(jakartaObj.contains("text"));

    assertTrue(gsonObj.remove("text"));
    assertTrue(jacksonObj.remove("text"));
    assertTrue(jackson3Obj.remove("text"));
    assertTrue(orgObj.remove("text"));
    assertTrue(jakartaObj.remove("text"));

    assertFalse(gsonObj.remove("text"));
    assertFalse(jacksonObj.remove("text"));
    assertFalse(jackson3Obj.remove("text"));
    assertFalse(orgObj.remove("text"));
    assertFalse(jakartaObj.remove("text"));

    assertFalse(gsonObj.contains("text"));
    assertFalse(jacksonObj.contains("text"));
    assertFalse(jackson3Obj.contains("text"));
    assertFalse(orgObj.contains("text"));
    assertFalse(jakartaObj.contains("text"));
  }

  @Test
  public void testEquals() {
    assertEquals(new GsonJsonObject((JsonObject) gsonObj.getSource()), gsonObj);
    assertEquals(new JacksonJsonObject((ObjectNode) jacksonObj.getSource()), jacksonObj);
    assertEquals(
        new Jackson3JsonObject((tools.jackson.databind.node.ObjectNode) jackson3Obj.getSource()),
        jackson3Obj);
    assertEquals(new OrgJsonObject((JSONObject) orgObj.getSource()), orgObj);
    assertEquals(new JakartaJsonObject((jakarta.json.JsonObject) jakartaObj.getSource()),
        jakartaObj);

    assertNotEquals(gsonObj, jacksonObj);
    assertNotEquals(gsonObj, jackson3Obj);
    assertNotEquals(gsonObj, orgObj);
    assertNotEquals(gsonObj, jakartaObj);
    assertNotEquals(jacksonObj, jackson3Obj);
    assertNotEquals(jacksonObj, orgObj);
    assertNotEquals(jacksonObj, jakartaObj);
    assertNotEquals(jackson3Obj, orgObj);
    assertNotEquals(jackson3Obj, jakartaObj);
    assertNotEquals(orgObj, jakartaObj);
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
    assertEquals(new GsonJsonValue((JsonElement) gsonObj.getSource()), gsonObj.asValue());
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
    assertEquals(new JacksonJsonValue((JsonNode) jacksonObj.getSource()), jacksonObj.asValue());
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

  @Test
  public void testJackson3JsonObjectState() {
    assertTrue(jackson3Obj.isObject());
    assertFalse(jackson3Obj.isArray());
    assertFalse(jackson3Obj.isString());
    assertFalse(jackson3Obj.isBoolean());
    assertFalse(jackson3Obj.isNumber());
    assertFalse(jackson3Obj.isNull());

    assertSame(jackson3Obj, jackson3Obj.asObject());
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asArray();
    });
    assertEquals(new Jackson3JsonValue((tools.jackson.databind.JsonNode) jackson3Obj.getSource()),
        jackson3Obj.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jackson3Obj.asBigDecimal();
    });
    assertTrue(jackson3Obj.getSource() instanceof tools.jackson.databind.node.ObjectNode);
  }

  @Test
  public void testOrgJsonObjectState() {
    assertTrue(orgObj.isObject());
    assertFalse(orgObj.isArray());
    assertFalse(orgObj.isString());
    assertFalse(orgObj.isBoolean());
    assertFalse(orgObj.isNumber());
    assertFalse(orgObj.isNull());

    assertSame(orgObj, orgObj.asObject());
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asArray();
    });
    assertEquals(new OrgJsonValue((JSONObject) orgObj.getSource()), orgObj.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      orgObj.asBigDecimal();
    });
    assertTrue(orgObj.getSource() instanceof JSONObject);
  }

  @Test
  public void testJakartaJsonObjectState() {
    assertTrue(jakartaObj.isObject());
    assertFalse(jakartaObj.isArray());
    assertFalse(jakartaObj.isString());
    assertFalse(jakartaObj.isBoolean());
    assertFalse(jakartaObj.isNumber());
    assertFalse(jakartaObj.isNull());

    assertSame(jakartaObj, jakartaObj.asObject());
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asArray();
    });
    assertEquals(new JakartaJsonValue((jakarta.json.JsonObject) jakartaObj.getSource()),
        jakartaObj.asValue());
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asString();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asBoolean();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asInt();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asLong();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asBigInteger();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asDouble();
    });
    assertThrows(UnsupportedOperationException.class, () -> {
      jakartaObj.asBigDecimal();
    });
    assertTrue(jakartaObj.getSource() instanceof jakarta.json.JsonObject);
  }

}
