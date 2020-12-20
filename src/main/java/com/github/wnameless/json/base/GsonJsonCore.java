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

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class GsonJsonCore implements JsonCore<GsonJsonValue> {

  private final Gson gson;

  public GsonJsonCore() {
    gson = new GsonBuilder().serializeNulls().create();
  }

  public GsonJsonCore(Gson gson) {
    this.gson = gson;
  }

  @Override
  public GsonJsonValue parse(String json) {
    return new GsonJsonValue(gson.fromJson(json, JsonElement.class));
  }

  @Override
  public GsonJsonValue parse(Reader jsonReader) throws IOException {
    return new GsonJsonValue(gson.fromJson(jsonReader, JsonElement.class));
  }

  @Override
  public GsonJsonValue parse(Object obj) {
    return new GsonJsonValue(gson.toJsonTree(obj));
  }

  @Override
  public GsonJsonArray createJsonArray() {
    return new GsonJsonArray(gson.fromJson("[]", JsonArray.class));
  }

  @Override
  public GsonJsonObject createJsonObject() {
    return new GsonJsonObject(gson.fromJson("{}", JsonObject.class));
  }

  @Override
  public GsonJsonValue createJsonNull() {
    return new GsonJsonValue(JsonNull.INSTANCE);
  }

  @Override
  public Map<String, Object> convertToMap(JsonValueExtra jsonValue) {
    return gson.fromJson((JsonElement) jsonValue.getSource(),
        new TypeToken<Map<String, Object>>() {}.getType());
  }

}
