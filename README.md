[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless/json-base/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless/json-base)
[![codecov](https://codecov.io/gh/wnameless/json-flattener/branch/master/graph/badge.svg)](https://codecov.io/gh/wnameless/json-base)

json-base
=============
A set of Java interfaces which defines JSON data and decouples JSON implementations such as Jackson, Gson and minimal-json...

## Purpose
To avoid json conversion between different JSON libararies in any JSON cosuming method by accepting a general JSON data inteface which wraps all kind of common JSON libararies.

## Maven Repo
```xml
<dependency>
	<groupId>com.github.wnameless</groupId>
	<artifactId>json-base</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Quick Start
```java
// You can let user to wrap their JSON implementation with JsonValueBase interface
public void acceptJsonVal(JsonValueBase  val) {
	...
}

// Or you can accept the GSON JsonElement and wrap it by GsonJsonValue within your labrary
public void  acceptJsonVal(JsonElement jsonElement) {
	JsonValueBase val = new GsonJsonValue(jsonElement);
	...
}

// Or you can accept the Jackson JsonNode and wrap it by JacksonJsonValue within your labrary
public void  acceptJsonVal(JsonNode jsonNode) {
	JsonValueBase val = new JacksonJsonValue(jsonNode);
	...
}

// Or you can accept the Minimal-JSON JsonValue and wrap it by MinimalJsonValue within your labrary
public void  acceptJsonVal(JsonValue jsonValue) {
	JsonValueBase val = new MinimalJsonValue(jsonValue);
	...
}
```

## Important
Althought this labrary privides wrappers for GSON, Jackson and minimal-json, you should  include the JSON impletation labrary you used in your dependencies.

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