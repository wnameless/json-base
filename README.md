![Maven Central Version](https://img.shields.io/maven-central/v/com.github.wnameless.json/json-base)
[![codecov](https://codecov.io/gh/wnameless/json-base/branch/master/graph/badge.svg)](https://codecov.io/gh/wnameless/json-base)

json-base
=============
A set of Java interfaces, which defines the common JSON data behaviors, can be used to decouple programming logic from JSON implementations such as Jackson, Gson, org.json and Jakarta... 

## Purpose
To avoid JSON conversion between different JSON libraries(Jackson, Gson, org.json, Jakarta...) in any JSON consuming Java method by creating generic JSON data interfaces which cover the common JSON data behaviors.

## Feature List
| Feature                                                                                                   | Description                                                                                                 |
| --------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| JSON-in JSON-out                                                                                          | Any JSON processing can always be started by JsonCore#parse and ended by Jsonable#toJson.                         |
| Any JSON implementation is completely decoupled                                                           | The JSON implementation can be switched by implementing corresponding interfaces.                                 |
| Popular JSON  implementations are supported by default                                                    | Jackson 2 and 3, Gson, org.json, Jakarta.                                                                         |
| Not involved in any Object serialization or deserialization                                               | The json-base lib just focuses on JSON data itself and nothing more.                                              |
| All special features of the selected JSON implementation are kept                                         | For example, the Gson object, which is used to create GsonJsonCore, can be configurated in advanced.            |
| All special manipulations, which require original Java types in selected JSON implementation, are capable | For example, the JsonNode object of Jackson library can always be retrieved by calling JsonSource#getSource.  |
| Java 9 modularity                                                                                         | This lib supports Java 9 modularity, but keeps Java 8 as the minimum version. Since v3.0.0, Java 17 is required. |
| Fully tested                                                                                              | The code coverage of json-base is 100%.                                                                           |

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
User can now program zir library logic based on the JSON data wrapper interfaces.
```java
public void acceptJsonVal(JsonValueBase<?> val) {
  ...
}

// At the same time, user can consume popular JSON implementations easily in zir library
public void acceptJacksonVal(JsonNode jsonNode) {
  JsonValueCore<JacksonJsonValue> val = new JacksonJsonValue(jsonNode);
  // If you are using Jackson 3:
  // JsonValueCore<Jackson3JsonValue> val = new Jackson3JsonValue(jsonNode);
  acceptJsonVal(val);
  ...
}

public void acceptGsonVal(JsonElement jsonElement) {
  JsonValueCore<GsonJsonValue> val = new GsonJsonValue(jsonElement);
  acceptJsonVal(val);
  ...
}

public void acceptOrgVal(JSONTokener jsonTokener) {
  JsonValueCore<OrgJsonValue> val = new OrgJsonValue(jsonTokener);
  acceptJsonVal(val);
  ...
}

public void acceptJakartaVal(JsonValue jsonValue) {
  JsonValueCore<JakartaJsonValue> val = new JakartaJsonValue(jsonValue);
  acceptJsonVal(val);
  ...
}
```

Sometimes, you may need a JSON parser in your library. Here is the wrapper:
```java
// GsonJsonCore, OrgJsonCore and JakartaJsonCore are also available
JsonCore<?> jsonCore = new JacksonJsonCore(); // new Jackson3JsonCore() if you are using Jackson 3
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
Although this library provides wrappers for Jackson 2 and 3, Gson, org.json and Jakarta, you still need to include the JSON implementation library which you are using in your dependencies.

## JSON data common interfaces
If user wants to implement zir own JSON data wrappers, here are some interfaces to work with.

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
  
  default Stream<JVB> stream() {
    return StreamSupport.stream(spliterator(), false);
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
  
  default Stream<Entry<String, JVB>> stream() {
    return StreamSupport.stream(spliterator(), false);
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
