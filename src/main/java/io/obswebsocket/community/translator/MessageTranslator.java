package io.obswebsocket.community.translator;

public interface MessageTranslator {
  <T> T fromJson(String json, Class<T> clazz);
  String toJson(Object object);
}
