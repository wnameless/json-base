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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import jakarta.json.Json;
import jakarta.json.JsonValue;

public class JsonCoreTest {

  String json =
      "{\"str\":\"text\",\"num\":[123,1234567890123456789,45.67,1234567890123456789012345678901234567890,45.678912367891236789123678912367891236789123],\"bool\":true,\"obj\":null}";

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

  JsonValueCore<?> jsonValue;

  @Test
  public void testConstructorException() {
    assertThrows(NullPointerException.class, () -> {
      new GsonJsonCore(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JacksonJsonCore(null);
    });
    assertThrows(NullPointerException.class, () -> {
      new JakartaJsonCore(null);
    });
  }

  @Test
  public void testGsonJsonCore() throws IOException {
    Gson gson = new Gson();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    jsonValue = new GsonJsonValue(jsonElement);
    assertNotEquals(jsonValue, new GsonJsonCore().parse(json));

    gson = new GsonBuilder().serializeNulls().create();
    jsonElement = gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    jsonValue = new GsonJsonValue(jsonElement);

    assertEquals(jsonValue, new GsonJsonCore(gson).parse(json));
    assertEquals(jsonValue,
        new GsonJsonCore(gson).parse(new StringReader(json)));

    assertThrows(RuntimeException.class, () -> {
      new GsonJsonCore().parse("\"abc");
    });
  }

  @Test
  public void testJacksonJsonCore() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.valueToTree(jo);
    jsonValue = new OrgJsonValue(json);
    assertNotEquals(jsonValue, new JacksonJsonCore().parse(json));

    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    jsonNode = mapper.valueToTree(jo);
    jsonValue = new JacksonJsonValue(jsonNode);

    assertEquals(jsonValue, new JacksonJsonCore(mapper).parse(json));
    assertEquals(jsonValue,
        new JacksonJsonCore(mapper).parse(new StringReader(json)));

    assertThrows(RuntimeException.class, () -> {
      new JacksonJsonCore().parse("\"abc");
    });
  }

  @Test
  public void testOrgJsonCore() throws IOException {
    JSONObject jsonObject = new JSONObject(jo);
    jsonValue = new OrgJsonValue(jsonObject);
    assertNotEquals(jsonValue, new OrgJsonCore().parse(json));

    jo.setObj(JSONObject.NULL);
    jsonObject = new JSONObject(jo);
    jsonValue = new OrgJsonValue(jsonObject);
    assertEquals(jsonValue, new OrgJsonCore().parse(json));

    jsonValue = new OrgJsonValue(new JSONTokener(json));

    assertEquals(jsonValue, new OrgJsonCore().parse(json));
    assertEquals(jsonValue, new OrgJsonCore().parse(new StringReader(json)));

    assertThrows(RuntimeException.class, () -> {
      new OrgJsonCore().parse("\"abc");
    });
  }

  @Test
  public void testJakartaJsonCore() throws IOException {
    jsonValue = new JakartaJsonValue(Json.createObjectBuilder().add("str", str)
        .add("num", Json.createArrayBuilder().add(i).add(l).add(d).add(bi)
            .add(bd).build())
        .add("bool", bool).add("obj", JsonValue.NULL).build());

    assertEquals(jsonValue, new JakartaJsonCore().parse(json));
    assertEquals(jsonValue,
        new JakartaJsonCore().parse(new StringReader(json)));

    assertThrows(RuntimeException.class, () -> {
      new JakartaJsonCore().parse("\"abc");
    });
  }

  @Test
  public void testJakartaJsonCoreWithReaderFactory() throws IOException {
    JakartaJsonCore jjc =
        new JakartaJsonCore(Json.createReaderFactory(new HashMap<>()));

    jsonValue = new JakartaJsonValue(Json.createObjectBuilder().add("str", str)
        .add("num", Json.createArrayBuilder().add(i).add(l).add(d).add(bi)
            .add(bd).build())
        .add("bool", bool).add("obj", JsonValue.NULL).build());

    assertEquals(jsonValue, jjc.parse(json));
    assertEquals(jsonValue, jjc.parse(new StringReader(json)));

    assertThrows(RuntimeException.class, () -> {
      jjc.parse("\"abc");
    });
  }

}
