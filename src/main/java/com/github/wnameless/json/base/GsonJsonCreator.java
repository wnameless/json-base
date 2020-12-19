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

import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonJsonCreator
    implements JsonCreator<GsonJsonValue, GsonJsonArray, GsonJsonObject> {

  private final Gson gson;

  public GsonJsonCreator(Gson gson) {
    this.gson = gson;
  }

  @Override
  public GsonJsonValue parse(String json) {
    return new GsonJsonValue(gson.fromJson(json, JsonElement.class));
  }

  @Override
  public GsonJsonValue parse(Reader jsonReader) {
    return new GsonJsonValue(gson.fromJson(jsonReader, JsonElement.class));
  }

  @Override
  public GsonJsonArray createJsonArray() {
    return new GsonJsonArray(gson.fromJson("[]", JsonArray.class));
  }

  @Override
  public GsonJsonObject createJsonObject() {
    return new GsonJsonObject(gson.fromJson("{}", JsonObject.class));
  }

}
