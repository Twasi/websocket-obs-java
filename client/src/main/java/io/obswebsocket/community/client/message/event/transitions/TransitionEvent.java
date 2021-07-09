package io.obswebsocket.community.client.message.event.transitions;

import io.obswebsocket.community.client.message.event.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
abstract class TransitionEvent extends Event {

  protected TransitionEvent(Type eventType, Category category) {
    super(eventType, category);
  }

  @Getter
  @ToString
  public static class Data {

    private String transitionName; // TODO TransitionEvent.Data
  }
}
