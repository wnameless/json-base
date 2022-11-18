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
	<version>${newestVersion}</version>
     <!-- Newest version shows in the maven-central badge above -->
</dependency>
```

## Quick Start
User can now program zir library logic focus to the JSON data wrapper interfaces.
```java
public void acceptJsonVal(JsonValueBase<?> val) {
  ...
}

// At the same time, user can consume popular JSON implementations easily in zir library
public void  acceptGsonVal(JsonElement jsonElement) {
  JsonValueCore<GsonJsonValue> val = new GsonJsonValue(jsonElement);
  acceptJsonVal(val);
  ...
}

public void  acceptJacksonVal(JsonNode jsonNode) {
  JsonValueCore<JacksonJsonValue> val = new JacksonJsonValue(jsonNode);
  acceptJsonVal(val);
  ...
}
```

Sometimes, you may need a JSON producer in your library. Here is the wrapper:
```java
// GsonJsonCore is also available
JsonCore<?> jsonCore = new JacksonJsonCore();
JsonValueCore<?> val = jsonCore.parse("{\"abc\":123}");
```

Demo of some operations:
```java
JsonObjectCore<?> obj = val.asObject();
obj.set("def", jsonCore.parse("[4.56,true]"));

if (obj.get("abc").isNumber()) {
  System.out.println(obj.get("abc").asInt());
  // 123
  
  // User can chose what kind of Java Number zir want to use
  System.out.println(obj.get("abc").asBigInteger());
  // 123
  
  // The #asNumber method creates a proper Java Number by the size and scale of numeric
  System.out.println(obj.get("abc").asNumber() instanceof Integer);
  // true
}

if (obj.get("def").isArray()) {
  JsonArrayCore<?> ary = obj.get("def").asArray();
  System.out.println(ary.get(0).asDouble());
  // 4.56
  
  System.out.println(ary.get(0).asNumber() instanceof Double);
  // true
  
  // Any JsonArrayBase can be converted into a List
  System.out.println(ary.toList());
  // [4.56, true]
}

// Any JsonObjectBase can be converted into a Map
System.out.println(obj.toMap());
// {abc=123, def=[4.56, true]}

// All JSON data wrapper can be converted into a JSON string
System.out.println(obj.toJson());
// {"abc":123,"def":[4.56,true]}

// JsonPrinter can either minimal or pretty print any JSON string
System.out.println(JsonPrinter.prettyPrint(obj.toJson()));
// {
//   "abc" : 123,
//   "def" : [ 4.56, true ]
// }
```

## Important
Althought this labrary privides wrappers for Gson and Jackson, you still need to include the JSON implementation library which you are using in your dependencies.

## JSON data common interfaces
If user want to implement zir own JSON data wappers, here are some interfaces to work with.

For immutable JSON data: JsonValueBase, JsonArrayBase, JsonObjectBase <br>
For mutable JSON data: JsonCore, JsonValueCore, JsonArrayCore, JsonObjectCore
### JsonCore
```java
public interface JsonCore<JVC extends JsonValueCore<?>> {

  JsonValueCore<JVC> parse(String json);

  JsonValueCore<JVC> parse(Reader jsonReader) throws IOException;

}
```

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

### JsonValueCore
```java
public interface JsonValueCore<JVC extends JsonValueCore<?>>
    extends JsonValueBase<JVC>, JsonSource {

  JsonObjectCore<JVC> asObject();

  JsonArrayCore<JVC> asArray();

  JsonValueCore<JVC> asValue();

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

### JsonArrayCore
```java
public interface JsonArrayCore<JVC extends JsonValueCore<?>>
    extends JsonArrayBase<JVC>, JsonValueCore<JVC> {

  void add(JsonSource jsonSource);

  void set(int index, JsonSource jsonSource);

  boolean remove(int index);

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

### JsonObjectCore
```java
public interface JsonObjectCore<JVC extends JsonValueCore<?>>
    extends JsonObjectBase<JVC>, JsonValueCore<JVC> {

  void set(String name, JsonSource jsonSource);

  boolean remove(String name);

}
```
