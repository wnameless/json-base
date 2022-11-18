module com.github.wnameless.json.base {
  requires static transitive com.fasterxml.jackson.core;
  requires static transitive com.fasterxml.jackson.databind;
  requires static transitive com.google.gson;

  exports com.github.wnameless.json.base;
}