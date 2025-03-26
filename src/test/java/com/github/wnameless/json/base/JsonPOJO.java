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

import java.util.List;

public class JsonPOJO {

  private String str;
  private List<?> num;
  private boolean bool;
  private byte[] bytes;
  private Object obj;

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public List<?> getNum() {
    return num;
  }

  public void setNum(List<?> num) {
    this.num = num;
  }

  public boolean isBool() {
    return bool;
  }

  public void setBool(boolean bool) {
    this.bool = bool;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }

}
