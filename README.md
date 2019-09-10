[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.json/json-base/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.json/json-base)
[![codecov](https://codecov.io/gh/wnameless/json-base/branch/master/graph/badge.svg)](https://codecov.io/gh/wnameless/json-base)

json-base
=============
A set of Java interfaces which decouples JSON implementations such as Jackson, Gson and minimal-json...

## Purpose
To avoid JSON conversion between different JSON libararies(Jackson, Gson...) in any JSON cosuming Java method by creating generic JSON data intefaces which cover the common JSON data behavior.

## Maven Repo
```xml
<dependency>
	<groupId>com.github.wnameless.json</groupId>
	<artifactId>json-base</artifactId>
	<version>1.0.0</version>
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

// Or you can accept the minimal-json JsonValue and wrap it by MinimalJsonValue within your library
public void  acceptMinimalJsonVal(JsonValue jsonValue) {
	JsonValueBase val = new MinimalJsonValue(jsonValue);
	...
}
```

## Important
Althought this labrary privides wrappers for Gson, Jackson and minimal-json, you should  include the JSON impletation library you used in your dependencies.

## JSON data common interfaces
### JsonValueBase
```java
public interface JsonValueBase<JV extends JsonValueBase<?>> {

  public boolean isObject();

  public boolean isArray();

  public boolean isNumber();

  public boolean isString();

  public boolean isBoolean();

  public boolean isNull();

  public JsonObjectBase<JV> asObject();

  public JsonArrayBase<JV> asArray();

  public int asInt();

  public long asLong();

  public double asDouble();

  public String asString();

  public boolean asBoolean();

}
```

### JsonArrayBase
```java
public interface JsonArrayBase<JV extends JsonValueBase<?>>
    extends Iterable<JV> {

  public JV get(int index);

  public Iterator<JV> iterator();

}

```

### JsonObjectBase
```java
public interface JsonObjectBase<JV extends JsonValueBase<?>>
    extends Iterable<Entry<String, JV>> {

  public JV get(String name);

}

```