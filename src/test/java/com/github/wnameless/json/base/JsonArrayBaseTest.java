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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import jakarta.json.Json;

public class JsonArrayBaseTest {

  String str = "text";
  int i = 123;
  long l = 1234567890123456789L;
  double d = 45.67;
  boolean bool = true;
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
      setObj(obj);
    }
  };

  JsonArrayBase<GsonJsonValue> gsonAry;
  JsonArrayBase<JacksonJsonValue> jacksonAry;
  JsonArrayBase<OrgJsonValue> orgAry;
  JsonArrayBase<JakartaJsonValue> jakartaAry;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement = gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonAry = new GsonJsonValue(jsonElement).asObject().get("num").asArray();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonAry = new JacksonJsonValue(jsonNode).asObject().get("num").asArray();

    jo.setObj(JSONObject.NULL);
    orgAry = new OrgJsonValue(new JSONObject(jo)).asObject().get("num").asArray();

    jakartaAry = new JakartaJsonArray(
        Json.createArrayBuilder().add(i).add(l).add(d).add(bi).add(bd).build());
  }

  @Test
  public void testGet() {
    assertEquals(i, gsonAry.get(0).asNumber());
    assertEquals(l, gsonAry.get(1).asNumber());
    assertEquals(d, gsonAry.get(2).asNumber());
    assertEquals(bi, gsonAry.get(3).asNumber());
    assertEquals(bd, gsonAry.get(4).asNumber());

    assertEquals(i, jacksonAry.get(0).asNumber());
    assertEquals(l, jacksonAry.get(1).asNumber());
    assertEquals(d, jacksonAry.get(2).asNumber());
    assertEquals(bi, jacksonAry.get(3).asNumber());
    assertEquals(bd, jacksonAry.get(4).asNumber());

    assertEquals(i, orgAry.get(0).asNumber());
    assertEquals(l, orgAry.get(1).asNumber());
    assertEquals(d, orgAry.get(2).asNumber());
    assertEquals(bi, orgAry.get(3).asNumber());
    assertEquals(bd, orgAry.get(4).asNumber());

    assertEquals(i, jakartaAry.get(0).asNumber());
    assertEquals(l, jakartaAry.get(1).asNumber());
    assertEquals(d, jakartaAry.get(2).asNumber());
    assertEquals(bi, jakartaAry.get(3).asNumber());
    assertEquals(bd, jakartaAry.get(4).asNumber());
  }

  @Test
  public void testSize() {
    assertEquals(5, gsonAry.size());
    assertEquals(5, jacksonAry.size());
    assertEquals(5, orgAry.size());
    assertEquals(5, jakartaAry.size());
  }

  @Test
  public void testIsEmpty() {
    assertFalse(gsonAry.isEmpty());
    assertFalse(jacksonAry.isEmpty());
    assertFalse(orgAry.isEmpty());
    assertFalse(jakartaAry.isEmpty());

    gsonAry = new GsonJsonCore().parse("[]").asArray();
    jacksonAry = new JacksonJsonCore().parse("[]").asArray();
    orgAry = new OrgJsonCore().parse("[]").asArray();
    jakartaAry = new JakartaJsonCore().parse("[]").asArray();

    assertTrue(gsonAry.isEmpty());
    assertTrue(jacksonAry.isEmpty());
    assertTrue(orgAry.isEmpty());
    assertTrue(jakartaAry.isEmpty());
  }

  @Test
  public void testToList() {
    List<Object> num = new ArrayList<>();
    num.addAll(Arrays.asList(i, l, d, bi, bd));

    assertEquals(num, gsonAry.toList());
    assertEquals(num, jacksonAry.toList());
    assertEquals(num, orgAry.toList());
    assertEquals(num, jakartaAry.toList());
  }

  @Test
  public void testStream() {
    assertEquals(StreamSupport.stream(gsonAry.spliterator(), false).collect(Collectors.toList()),
        gsonAry.stream().collect(Collectors.toList()));
    assertEquals(StreamSupport.stream(jacksonAry.spliterator(), false).collect(Collectors.toList()),
        jacksonAry.stream().collect(Collectors.toList()));
    assertEquals(StreamSupport.stream(orgAry.spliterator(), false).collect(Collectors.toList()),
        orgAry.stream().collect(Collectors.toList()));
    assertEquals(StreamSupport.stream(jakartaAry.spliterator(), false).collect(Collectors.toList()),
        jakartaAry.stream().collect(Collectors.toList()));
  }

}
