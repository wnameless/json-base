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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * 
 * The GSON implementation of {@link JsonCore}.
 * 
 * @author Wei-Ming Wu
 *
 */
public class GsonJsonCore implements JsonCore<GsonJsonValue> {

  private final Gson gson;

  private static class LazyHolder {
    public static final Gson INSTANCE = new GsonBuilder().serializeNulls().create();
  }

  private Gson getInstance() {
    return gson == null ? LazyHolder.INSTANCE : gson;
  }

  public GsonJsonCore() {
    gson = null;
  }

  public GsonJsonCore(Gson gson) {
    if (gson == null) throw new NullPointerException();
    this.gson = gson;
  }

  @Override
  public GsonJsonValue parse(String json) {
    return new GsonJsonValue(getInstance().fromJson(json, JsonElement.class));
  }

  @Override
  public GsonJsonValue parse(Reader jsonReader) throws IOException {
    return new GsonJsonValue(getInstance().fromJson(jsonReader, JsonElement.class));
  }

}
