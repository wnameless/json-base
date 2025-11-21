module com.github.wnameless.json.base {
  requires static tools.jackson.databind;
  requires static com.fasterxml.jackson.core;
  requires static com.fasterxml.jackson.databind;
  requires static com.google.gson;
  requires static org.json;
  requires static jakarta.json;
  requires com.fasterxml.jackson.annotation;

  exports com.github.wnameless.json.base;
}
