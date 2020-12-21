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
    if (json == null) new NullPointerException();
    StringBuilder minimalPrintBuilder = new StringBuilder();

    boolean inQuote = false;
    for (char jsonChar : json.toCharArray()) {
      switch (jsonChar) {
        case '"':
          inQuote = !inQuote;
          minimalPrintBuilder.append(jsonChar);
          break;
        default:
          if (inQuote || !Character.toString(jsonChar).matches("\\s")) {
            minimalPrintBuilder.append(jsonChar);
          }
      }
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
    if (json == null) new NullPointerException();
    StringBuilder prettyPrintBuilder = new StringBuilder();

    int indentLevel = 0;
    boolean inQuote = false;
    boolean inBracket = false;
    for (char jsonChar : json.toCharArray()) {
      switch (jsonChar) {
        case '"':
          inQuote = !inQuote;
          prettyPrintBuilder.append(jsonChar);
          break;
        case '{':
          prettyPrintBuilder.append(jsonChar);
          if (!inQuote) {
            indentLevel++;
            appendNewLine(indentLevel, prettyPrintBuilder);
          }
          break;
        case '}':
          if (!inQuote) {
            indentLevel--;
            appendNewLine(indentLevel, prettyPrintBuilder);
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
              appendNewLine(indentLevel, prettyPrintBuilder);
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
    }

    return prettyPrintBuilder.toString();
  }

  private static void appendNewLine(int indentLevel,
      StringBuilder stringBuilder) {
    stringBuilder.append('\n');
    for (int i = 0; i < indentLevel; i++) {
      stringBuilder.append(' ');
      stringBuilder.append(' ');
    }
  }

}
