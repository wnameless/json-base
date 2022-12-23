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
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class JsonPrinterTest {

  @Test
  public void testException() {
    assertThrows(NullPointerException.class, () -> {
      JsonPrinter.minimalPrint(null);
    });
    assertThrows(NullPointerException.class, () -> {
      JsonPrinter.prettyPrint(null);
    });
  }

  @Test
  public void testMinimalPrint() throws IOException {
    URL url = Resources.getResource("minimal.json");
    String minimalJson = Resources.toString(url, Charsets.UTF_8);
    url = Resources.getResource("pretty.json");
    String prettyJson = Resources.toString(url, Charsets.UTF_8);

    assertEquals(minimalJson, JsonPrinter.minimalPrint(prettyJson));
  }

  @Test
  public void testPrettyPrint() throws IOException {
    URL url = Resources.getResource("minimal.json");
    String minimalJson = Resources.toString(url, Charsets.UTF_8);
    url = Resources.getResource("pretty.json");
    String prettyJson = Resources.toString(url, Charsets.UTF_8);

    assertEquals(prettyJson, JsonPrinter.prettyPrint(minimalJson));
    assertEquals(prettyJson, JsonPrinter.prettyPrint(prettyJson));
  }

  @Test
  public void testMinimalPrintWithDoubleQuotesEscaped() throws IOException {
    URL url = Resources.getResource("minimal-double-quotes-escaped.json");
    String minimalJson = Resources.toString(url, Charsets.UTF_8);
    url = Resources.getResource("pretty-double-quotes-escaped.json");
    String prettyJson = Resources.toString(url, Charsets.UTF_8);

    assertEquals(minimalJson, JsonPrinter.minimalPrint(prettyJson));
  }

  @Test
  public void testPrettyPrintWithDoubleQuotesEscaped() throws IOException {
    URL url = Resources.getResource("minimal-double-quotes-escaped.json");
    String minimalJson = Resources.toString(url, Charsets.UTF_8);
    url = Resources.getResource("pretty-double-quotes-escaped.json");
    String prettyJson = Resources.toString(url, Charsets.UTF_8);

    assertEquals(prettyJson, JsonPrinter.prettyPrint(minimalJson));
  }

}
