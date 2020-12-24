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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class JsonObjectExtraTest {

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
  public void testSetRemove() {
    assertFalse(gsonObj.contains("text"));
    assertFalse(jacksonObj.contains("text"));

    gsonObj.set("text", new GsonJsonCore().parse("\"str\""));
    jacksonObj.set("text", new JacksonJsonCore().parse("\"str\""));

    assertEquals(5, gsonObj.size());
    assertEquals(5, jacksonObj.size());

    assertEquals("str", gsonObj.get("text").asString());
    assertEquals("str", jacksonObj.get("text").asString());

    assertTrue(gsonObj.contains("text"));
    assertTrue(jacksonObj.contains("text"));

    gsonObj.remove("text");
    jacksonObj.remove("text");

    assertFalse(gsonObj.contains("text"));
    assertFalse(jacksonObj.contains("text"));
  }

}
