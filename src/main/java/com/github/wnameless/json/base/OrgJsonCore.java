/*
 *
 * Copyright 2022 Wei-Ming Wu
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

import org.json.JSONTokener;

public class OrgJsonCore implements JsonCore<OrgJsonValue> {

  @Override
  public OrgJsonValue parse(String json) {
    return new OrgJsonValue(new JSONTokener(json));
  }

  @Override
  public OrgJsonValue parse(Reader jsonReader) throws IOException {
    return new OrgJsonValue(new JSONTokener(jsonReader));
  }

}
