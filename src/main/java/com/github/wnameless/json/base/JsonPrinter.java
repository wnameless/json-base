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

/**
 * 
 * {@link JsonPrinter} reprints any JSON input into minimal or pretty form. It
 * only uses Java native string processing to generate the output and no
 * additional explicit JSON library is required.
 * 
 * @author Wei-Ming Wu
 *
 */
public final class JsonPrinter {

  private JsonPrinter() {}

  /**
   * Returns a minimal print JSON of any JSON input.
   * 
   * @param json
   *          any JSON
   * @return a minimal print JSON
   */
  public static String minimalPrint(String json) {
    if (json == null) new NullPointerException();
    StringBuilder minimalPrintBuilder = new StringBuilder();

    int lookback = -1;
    int backslashCount = 0;
    boolean inQuote = false;
    for (char jsonChar : json.toCharArray()) {
      switch (jsonChar) {
        case '"':
          if (lookback != '\\' || backslashCount % 2 == 0) {
            inQuote = !inQuote;
            minimalPrintBuilder.append(jsonChar);
            break;
          }
        default:
          if (inQuote || !Character.toString(jsonChar).matches("\\s")) {
            minimalPrintBuilder.append(jsonChar);
          }
      }
      if (jsonChar == '\\') {
        backslashCount++;
      } else {
        backslashCount = 0;
      }
      lookback = jsonChar;
    }

    return minimalPrintBuilder.toString();
  }

  /**
   * Returns a pretty print JSON of any JSON input.
   * 
   * @param json
   *          any JSON
   * @return a pretty print JSON
   */
  public static String prettyPrint(String json) {
    return prettyPrint(json, "  ");
  }

  /**
   * Returns a pretty print JSON of any JSON input.
   * 
   * @param json
   *          any JSON
   * @param indentStr
   *          a string to use as an indent
   * @return a pretty print JSON
   */
  public static String prettyPrint(String json, String indentStr) {
    if (json == null) new NullPointerException();
    StringBuilder prettyPrintBuilder = new StringBuilder();

    int lookback = -1;
    int backslashCount = 0;
    int indentLevel = 0;
    boolean inQuote = false;
    boolean inBracket = false;
    for (char jsonChar : json.toCharArray()) {
      switch (jsonChar) {
        case '"':
          if (lookback != '\\' || backslashCount % 2 == 0) {
            inQuote = !inQuote;
            prettyPrintBuilder.append(jsonChar);
            break;
          }
        case '{':
          prettyPrintBuilder.append(jsonChar);
          if (!inQuote) {
            indentLevel++;
            appendNewLine(prettyPrintBuilder, indentLevel, indentStr);
          }
          break;
        case '}':
          if (!inQuote) {
            indentLevel--;
            appendNewLine(prettyPrintBuilder, indentLevel, indentStr);
          }
          prettyPrintBuilder.append(jsonChar);
          break;
        case '[':
          prettyPrintBuilder.append(jsonChar);
          if (!inQuote) {
            inBracket = true;
            prettyPrintBuilder.append(' ');
          }
          break;
        case ']':
          if (!inQuote) {
            inBracket = false;
            prettyPrintBuilder.append(' ');
          }
          prettyPrintBuilder.append(jsonChar);
          break;
        case ',':
          prettyPrintBuilder.append(jsonChar);
          if (!inQuote) {
            if (inBracket) {
              prettyPrintBuilder.append(' ');
            } else {
              appendNewLine(prettyPrintBuilder, indentLevel, indentStr);
            }
          }
          break;
        case ':':
          if (inQuote) {
            prettyPrintBuilder.append(jsonChar);
          } else {
            prettyPrintBuilder.append(' ');
            prettyPrintBuilder.append(jsonChar);
            prettyPrintBuilder.append(' ');
          }
          break;
        default:
          if (inQuote || !Character.toString(jsonChar).matches("\\s")) {
            prettyPrintBuilder.append(jsonChar);
          }
      }
      if (jsonChar == '\\') {
        backslashCount++;
      } else {
        backslashCount = 0;
      }
      lookback = jsonChar;
    }

    return prettyPrintBuilder.toString();
  }

  private static void appendNewLine(StringBuilder stringBuilder,
      int indentLevel, String indentStr) {
    stringBuilder.append('\n');
    for (int i = 0; i < indentLevel; i++) {
      stringBuilder.append(indentStr);
    }
  }

}
