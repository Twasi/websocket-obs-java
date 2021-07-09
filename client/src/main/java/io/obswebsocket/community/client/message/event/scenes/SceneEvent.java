package io.obswebsocket.community.client.message.event.scenes;

import io.obswebsocket.community.client.message.event.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
abstract class SceneEvent extends Event {

  protected SceneEvent(Type eventType, Category category) {
    super(eventType, category);
  }

  @Getter
  @ToString
  protected static class Data {

    private String sceneName;
  }
}
