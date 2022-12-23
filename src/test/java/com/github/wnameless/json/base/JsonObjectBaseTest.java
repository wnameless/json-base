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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import jakarta.json.Json;
import jakarta.json.JsonValue;

public class JsonObjectBaseTest {

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

  JsonObjectBase<GsonJsonValue> gsonObj;
  JsonObjectBase<JacksonJsonValue> jacksonObj;
  JsonObjectBase<OrgJsonValue> orgObj;
  JsonObjectBase<JakartaJsonValue> jakartaObj;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement = gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonObj = new GsonJsonValue(jsonElement).asObject();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonObj = new JacksonJsonValue(jsonNode).asObject();

    jo.setObj(JSONObject.NULL);
    orgObj = new OrgJsonValue(new JSONObject(jo)).asObject();

    jakartaObj = new JakartaJsonValue(Json.createObjectBuilder().add("str", str)
        .add("num", Json.createArrayBuilder().add(i).add(l).add(d).add(bi).add(bd).build())
        .add("bool", bool).add("obj", JsonValue.NULL).build()).asObject();
  }

  @Test
  public void testNames() {
    assertArrayEquals(new String[] {"str", "num", "bool", "obj"},
        Iterators.toArray(gsonObj.names(), String.class));

    assertArrayEquals(new String[] {"str", "num", "bool", "obj"},
        Iterators.toArray(jacksonObj.names(), String.class));

    assertEquals(new HashSet<>(Arrays.asList(new String[] {"str", "num", "bool", "obj"})),
        new HashSet<>(Arrays.asList(Iterators.toArray(orgObj.names(), String.class))));

    assertArrayEquals(new String[] {"str", "num", "bool", "obj"},
        Iterators.toArray(jakartaObj.names(), String.class));
  }

  @Test
  public void testContains() {
    assertFalse(gsonObj.contains("text"));
    assertFalse(jacksonObj.contains("text"));
    assertFalse(orgObj.contains("text"));
    assertFalse(jakartaObj.contains("text"));

    assertTrue(gsonObj.contains("str"));
    assertTrue(jacksonObj.contains("str"));
    assertTrue(orgObj.contains("str"));
    assertTrue(jakartaObj.contains("str"));
  }

  @Test
  public void testGet() {
    assertEquals(str, gsonObj.get("str").asString());
    assertEquals(Arrays.asList(i, l, d, bi, bd), gsonObj.get("num").asArray().toList());
    assertEquals(bool, gsonObj.get("bool").asBoolean());
    assertEquals(null, gsonObj.get("obj").asNull());
    assertEquals(null, gsonObj.get("none"));

    assertEquals(str, jacksonObj.get("str").asString());
    assertEquals(Arrays.asList(i, l, d, bi, bd), jacksonObj.get("num").asArray().toList());
    assertEquals(bool, jacksonObj.get("bool").asBoolean());
    assertEquals(null, jacksonObj.get("obj").asNull());
    assertEquals(null, jacksonObj.get("none"));

    assertEquals(str, orgObj.get("str").asString());
    assertEquals(Arrays.asList(i, l, d, bi, bd), orgObj.get("num").asArray().toList());
    assertEquals(bool, orgObj.get("bool").asBoolean());
    assertEquals(null, orgObj.get("obj").asNull());
    assertEquals(null, orgObj.get("none"));

    assertEquals(str, jakartaObj.get("str").asString());
    assertEquals(Arrays.asList(i, l, d, bi, bd), jakartaObj.get("num").asArray().toList());
    assertEquals(bool, jakartaObj.get("bool").asBoolean());
    assertEquals(null, jakartaObj.get("obj").asNull());
    assertEquals(null, jakartaObj.get("none"));
  }

  @Test
  public void testSize() {
    assertEquals(4, gsonObj.size());
    assertEquals(4, jacksonObj.size());
    assertEquals(4, orgObj.size());
    assertEquals(4, jakartaObj.size());
  }

  @Test
  public void testIsEmpty() {
    assertFalse(gsonObj.isEmpty());
    assertFalse(jacksonObj.isEmpty());
    assertFalse(orgObj.isEmpty());
    assertFalse(jakartaObj.isEmpty());

    gsonObj = new GsonJsonCore().parse("{}").asObject();
    jacksonObj = new JacksonJsonCore().parse("{}").asObject();
    orgObj = new OrgJsonCore().parse("{}").asObject();
    jakartaObj = new JakartaJsonCore().parse("{}").asObject();

    assertTrue(gsonObj.isEmpty());
    assertTrue(jacksonObj.isEmpty());
    assertTrue(orgObj.isEmpty());
    assertTrue(jakartaObj.isEmpty());
  }

  @Test
  public void testToMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    List<Number> num = new ArrayList<>();
    num.addAll(Arrays.asList(i, l, d, bi, bd));
    map.put("str", str);
    map.put("num", num);
    map.put("bool", bool);
    map.put("obj", obj);

    assertEquals(map, gsonObj.toMap());
    assertEquals(map, jacksonObj.toMap());
    assertEquals(map, orgObj.toMap());
    assertEquals(map, jakartaObj.toMap());
  }

  @Test
  public void testStream() {
    assertEquals(StreamSupport.stream(gsonObj.spliterator(), false).collect(Collectors.toList()),
        gsonObj.stream().collect(Collectors.toList()));
    assertEquals(StreamSupport.stream(jacksonObj.spliterator(), false).collect(Collectors.toList()),
        jacksonObj.stream().collect(Collectors.toList()));
    assertEquals(StreamSupport.stream(orgObj.spliterator(), false).collect(Collectors.toList()),
        orgObj.stream().collect(Collectors.toList()));
    assertEquals(StreamSupport.stream(jakartaObj.spliterator(), false).collect(Collectors.toList()),
        jakartaObj.stream().collect(Collectors.toList()));
  }

}
