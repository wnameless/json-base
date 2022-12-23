module com.github.wnameless.json.base {
  requires static com.fasterxml.jackson.core;
  requires static com.fasterxml.jackson.databind;
  requires static com.google.gson;
  requires static org.json;
  requires static jakarta.json;

  exports com.github.wnameless.json.base;
}
