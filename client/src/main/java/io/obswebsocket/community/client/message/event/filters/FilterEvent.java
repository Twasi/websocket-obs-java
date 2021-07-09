package io.obswebsocket.community.client.message.event.filters;

import io.obswebsocket.community.client.message.event.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
abstract class FilterEvent extends Event {

  protected FilterEvent(Type eventType, Category category) {
    super(eventType, category);
  }

  @Getter
  @ToString
  public static class Data {

    private String filterName; // TODO FilterEvent.Data
  }
}
