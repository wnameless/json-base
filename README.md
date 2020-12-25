[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.json/json-base/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.json/json-base)
[![codecov](https://codecov.io/gh/wnameless/json-base/branch/master/graph/badge.svg)](https://codecov.io/gh/wnameless/json-base)

json-base
=============
A set of Java interfaces which decouples JSON implementations such as Jackson and Gson...

## Purpose
To avoid JSON conversion between different JSON libararies(Jackson, Gson...) in any JSON cosuming Java method by creating generic JSON data intefaces which cover the common JSON data behavior.

Java 9 Module is supported after v1.1.0, but the minimal Java version is remained Java 8.
## Maven Repo
```xml
<dependency>
	<groupId>com.github.wnameless.json</groupId>
	<artifactId>json-base</artifactId>
	<version>2.0.0</version>
</dependency>
```

## Quick Start
```java
// You can let user to wrap their JSON implementation with JsonValueBase interface
public void acceptJsonVal(JsonValueBase val) {
	...
}

// Or you can accept the Gson JsonElement and wrap it by GsonJsonValue within your library
public void  acceptGsonVal(JsonElement jsonElement) {
	JsonValueBase val = new GsonJsonValue(jsonElement);
	...
}

// Or you can accept the Jackson JsonNode and wrap it by JacksonJsonValue within your library
public void  acceptJacksonVal(JsonNode jsonNode) {
	JsonValueBase val = new JacksonJsonValue(jsonNode);
	...
}
```

## Important
Althought this labrary privides wrappers for Gson and Jackson, you still need to include the JSON implementation library which you are using in your dependencies.

## JSON data common interfaces
### JsonValueBase
```java
public interface JsonValueBase<JVB extends JsonValueBase<?>> extends Jsonable {

  public boolean isObject();

  public boolean isArray();

  public boolean isString();

  public boolean isBoolean();

  public boolean isNumber();

  public boolean isNull();

  public JsonObjectBase<JVB> asObject();

  public JsonArrayBase<JVB> asArray();

  public JsonValueBase<JVB> asValue();

  public String asString();

  public boolean asBoolean();

  public int asInt();

  public long asLong();

  public BigInteger asBigInteger();

  public double asDouble();

  public BigDecimal asBigDecimal();

  default Number asNumber() {
    return JsonValueUtils.toJavaNumber(asBigDecimal());
  }

  default Object asNull() {
    return null;
  }

}
```

### JsonArrayBase
```java
public interface JsonArrayBase<JVB extends JsonValueBase<?>>
    extends Iterable<JVB>, JsonValueBase<JVB> {

  JVB get(int index);

  int size();

  default boolean isEmpty() {
    return !iterator().hasNext();
  }

  default List<Object> toList() {
    return JsonValueUtils.toList(this);
  }

}
```

### JsonObjectBase
```java
public interface JsonObjectBase<JVB extends JsonValueBase<?>>
    extends Iterable<Entry<String, JVB>>, JsonValueBase<JVB> {

  Iterator<String> names();

  boolean contains(String name);

  JVB get(String name);

  int size();

  default boolean isEmpty() {
    return !iterator().hasNext();
  }

  default Map<String, Object> toMap() {
    return JsonValueUtils.toMap(this);
  }

}
```