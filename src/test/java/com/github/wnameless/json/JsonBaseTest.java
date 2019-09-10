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
package com.github.wnameless.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.EqualsTester;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import net.sf.rubycollect4j.Ruby;

public class JsonBaseTest {

  String str = "text";
  int i = 123;
  long l = 1234567890;
  double d = 45.67;
  boolean bool = true;
  Object obj = null;

  JsonObject jo = new JsonObject() {
    {
      setStr(str);
      setNum(new ArrayList<Number>() {
        private static final long serialVersionUID = 1L;
        {
          add(i);
          add(l);
          add(d);
        }
      });
      setBool(bool);
      setObj(obj);
    }
  };

  JsonValueBase<?> jsonValue;

  @Test
  public void testGsonValue() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement =
        gson.toJsonTree(jo, new TypeToken<JsonObject>() {}.getType());
    jsonValue = new GsonJsonValue(jsonElement);

    assertTrue(jsonValue.isObject());
    assertTrue(jsonValue.asObject().get("str").isString());
    assertEquals(str, jsonValue.asObject().get("str").asString());
    assertTrue(jsonValue.asObject().get("num").isArray());
    assertTrue(jsonValue.asObject().get("num").asArray().get(0).isNumber());
    assertEquals(i, jsonValue.asObject().get("num").asArray().get(0).asInt());
    assertTrue(jsonValue.asObject().get("num").asArray().get(1).isNumber());
    assertEquals(l, jsonValue.asObject().get("num").asArray().get(1).asLong());
    assertTrue(jsonValue.asObject().get("num").asArray().get(2).isNumber());
    assertEquals(d, jsonValue.asObject().get("num").asArray().get(2).asDouble(),
        0.0);
    assertTrue(jsonValue.asObject().get("bool").isBoolean());
    assertTrue(jsonValue.asObject().get("bool").asBoolean());
    assertTrue(jsonValue.asObject().get("obj").isNull());

    new EqualsTester().addEqualityGroup(jsonValue).testEquals();
    new EqualsTester().addEqualityGroup(jsonValue.asObject()).testEquals();
    new EqualsTester()
        .addEqualityGroup(jsonValue.asObject().get("num").asArray())
        .testEquals();
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
    assertTrue(jsonValue.asObject().get("num").asArray().get(1).isNumber());
    assertEquals(l, jsonValue.asObject().get("num").asArray().get(1).asLong());
    assertTrue(jsonValue.asObject().get("num").asArray().get(2).isNumber());
    assertEquals(d, jsonValue.asObject().get("num").asArray().get(2).asDouble(),
        0.0);
    assertTrue(jsonValue.asObject().get("bool").isBoolean());
    assertTrue(jsonValue.asObject().get("bool").asBoolean());
    assertTrue(jsonValue.asObject().get("obj").isNull());

    new EqualsTester().addEqualityGroup(jsonValue).testEquals();
    new EqualsTester().addEqualityGroup(jsonValue.asObject()).testEquals();
    new EqualsTester()
        .addEqualityGroup(jsonValue.asObject().get("num").asArray())
        .testEquals();
  }

  @Test
  public void testMinialJsonValue() throws JsonProcessingException {
    JsonValue jv = Json.parse(new ObjectMapper().writeValueAsString(jo));
    jsonValue = new MinimalJsonValue(jv);

    assertTrue(jsonValue.isObject());
    assertTrue(jsonValue.asObject().get("str").isString());
    assertEquals(str, jsonValue.asObject().get("str").asString());
    assertTrue(jsonValue.asObject().get("num").isArray());
    assertTrue(jsonValue.asObject().get("num").asArray().get(0).isNumber());
    assertEquals(i, jsonValue.asObject().get("num").asArray().get(0).asInt());
    assertTrue(jsonValue.asObject().get("num").asArray().get(1).isNumber());
    assertEquals(l, jsonValue.asObject().get("num").asArray().get(1).asLong());
    assertTrue(jsonValue.asObject().get("num").asArray().get(2).isNumber());
    assertEquals(d, jsonValue.asObject().get("num").asArray().get(2).asDouble(),
        0.0);
    assertTrue(jsonValue.asObject().get("bool").isBoolean());
    assertTrue(jsonValue.asObject().get("bool").asBoolean());
    assertTrue(jsonValue.asObject().get("obj").isNull());

    new EqualsTester().addEqualityGroup(jsonValue).testEquals();
    new EqualsTester().addEqualityGroup(jsonValue.asObject()).testEquals();
    new EqualsTester()
        .addEqualityGroup(jsonValue.asObject().get("num").asArray())
        .testEquals();
  }

  public class JsonObject implements Iterable<Entry<String, Object>> {

    private String str;
    private List<Number> num;
    private boolean bool;
    private Object obj;

    public String getStr() {
      return str;
    }

    public void setStr(String str) {
      this.str = str;
    }

    public List<Number> getNum() {
      return num;
    }

    public void setNum(List<Number> num) {
      this.num = num;
    }

    public boolean isBool() {
      return bool;
    }

    public void setBool(boolean bool) {
      this.bool = bool;
    }

    public Object getObj() {
      return obj;
    }

    public void setObj(Object obj) {
      this.obj = obj;
    }

    @Override
    public Iterator<Entry<String, Object>> iterator() {
      return Ruby.Hash.of("str", str, "num", num, "bool", bool, "obj", obj)
          .iterator();
    }

  }

}
