package com.github.wnameless.json.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;

public class OrgJsonObject implements JsonObjectCore<OrgJsonValue> {

  private final JSONObject jsonObject;

  public OrgJsonObject(JSONObject jsonObject) {
    if (jsonObject == null) throw new NullPointerException();
    this.jsonObject = jsonObject;
  }

  @Override
  public Iterator<String> names() {
    return jsonObject.keys();
  }

  @Override
  public boolean contains(String name) {
    return jsonObject.has(name);
  }

  @Override
  public OrgJsonValue get(String name) {
    return new OrgJsonValue(jsonObject.get(name));
  }

  @Override
  public int hashCode() {
    return jsonObject.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrgJsonObject)) return false;
    return jsonObject.similar(((OrgJsonObject) o).jsonObject);
  }

  @Override
  public String toString() {
    return jsonObject.toString();
  }

  @Override
  public int size() {
    return jsonObject.length();
  }

  @Override
  public Iterator<Entry<String, OrgJsonValue>> iterator() {
    return new OrgJsonEntryIterator(jsonObject);
  }

  private final class OrgJsonEntryIterator
      implements Iterator<Entry<String, OrgJsonValue>> {

    private final JSONObject jsonObject;
    private final Iterator<String> keys;

    private OrgJsonEntryIterator(JSONObject jsonObject) {
      this.jsonObject = jsonObject;
      keys = jsonObject.keys();
    }

    @Override
    public boolean hasNext() {
      return keys.hasNext();
    }

    @Override
    public Entry<String, OrgJsonValue> next() {
      String key = keys.next();
      return new AbstractMap.SimpleImmutableEntry<>(key,
          new OrgJsonValue(jsonObject.get(key)));
    }

  }

  @Override
  public boolean isObject() {
    return true;
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
  public String asString() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean asBoolean() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int asInt() {
    throw new UnsupportedOperationException();
  }

  @Override
  public long asLong() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BigInteger asBigInteger() {
    throw new UnsupportedOperationException();
  }

  @Override
  public double asDouble() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BigDecimal asBigDecimal() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toJson() {
    return toString();
  }

  @Override
  public OrgJsonObject asObject() {
    return this;
  }

  @Override
  public OrgJsonArray asArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OrgJsonValue asValue() {
    return new OrgJsonValue(jsonObject);
  }

  @Override
  public Object getSource() {
    return jsonObject;
  }

  @Override
  public void set(String name, JsonSource jsonValue) {
    jsonObject.put(name, jsonValue.getSource());
  }

  @Override
  public boolean remove(String name) {
    return jsonObject.remove(name) != null;
  }

}
