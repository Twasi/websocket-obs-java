package io.obswebsocket.community.client.message.event.filters;

import io.obswebsocket.community.client.message.event.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
abstract class SourceFilterEvent extends Event {

  protected SourceFilterEvent(Type eventType, Category category) {
    super(eventType, category);
  }

  @Getter
  @ToString
  public static class Data {

    private String sourceName; // TODO SourceFilterEvent.Data
    private String filterName; // TODO SourceFilterEvent.Data
  }
}
