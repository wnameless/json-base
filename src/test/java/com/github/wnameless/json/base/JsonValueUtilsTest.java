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
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class JsonValueUtilsTest {

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
      setNum(new ArrayList<Object>() {
        private static final long serialVersionUID = 1L;
        {
          add(i);
          add(l);
          add(d);
          add(bi);
          add(bd);
          add(ImmutableMap.of("abc", 123));
        }
      });
      setBool(bool);
      setObj(obj);
    }
  };

  JsonObjectBase<?> gsonObj;
  JsonObjectBase<?> jacksonObj;

  @BeforeEach
  public void init() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonElement jsonElement = gson.toJsonTree(jo, new TypeToken<JsonPOJO>() {}.getType());
    gsonObj = new GsonJsonValue(jsonElement).asObject();

    JsonNode jsonNode = new ObjectMapper().valueToTree(jo);
    jacksonObj = new JacksonJsonValue(jsonNode).asObject();
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void testToObject() {
    assertEquals(JsonValueUtils.toObject(gsonObj), JsonValueUtils.toObject(jacksonObj));

    assertThrows(IllegalStateException.class, () -> {
      JsonValueUtils.toObject(new JsonValueBase() {

        @Override
        public String toJson() {
          return null;
        }

        @Override
        public boolean isObject() {
          return false;
        }

        @Override
        public boolean isArray() {
          return false;
        }

        @Override
        public boolean isString() {
          return false;
        }

        @Override
        public boolean isBoolean() {
          return false;
        }

        @Override
        public boolean isNumber() {
          return false;
        }

        @Override
        public boolean isNull() {
          return false;
        }

        @Override
        public JsonObjectBase asObject() {
          return null;
        }

        @Override
        public JsonArrayBase asArray() {
          return null;
        }

        @Override
        public JsonValueBase asValue() {
          return null;
        }

        @Override
        public String asString() {
          return null;
        }

        @Override
        public boolean asBoolean() {
          return false;
        }

        @Override
        public int asInt() {
          return 0;
        }

        @Override
        public long asLong() {
          return 0;
        }

        @Override
        public BigInteger asBigInteger() {
          return null;
        }

        @Override
        public double asDouble() {
          return 0;
        }

        @Override
        public BigDecimal asBigDecimal() {
          return null;
        }

      });
    });
  }

  @Test
  public void testToJavaNumber() {
    BigDecimal bd = new BigDecimal("123");
    assertEquals(123, JsonValueUtils.toJavaNumber(bd));
    bd = new BigDecimal(123L);
    assertEquals(123, JsonValueUtils.toJavaNumber(bd));
    bd = new BigDecimal(12345678901L);
    assertEquals(12345678901L, JsonValueUtils.toJavaNumber(bd));
    bd = new BigDecimal("12345678901234567890");
    assertEquals(new BigInteger("12345678901234567890"), JsonValueUtils.toJavaNumber(bd));
    bd = new BigDecimal("123.456");
    assertEquals(123.456, JsonValueUtils.toJavaNumber(bd));
    assertTrue(JsonValueUtils.toJavaNumber(bd) instanceof Double);
    bd = new BigDecimal("123.4560");
    assertEquals(4, bd.scale());
    assertNotEquals(123.456, JsonValueUtils.toJavaNumber(bd));
    assertTrue(JsonValueUtils.toJavaNumber(bd) instanceof BigDecimal);
    assertEquals(123.456, JsonValueUtils.toJavaNumber(bd).doubleValue());
  }

}
