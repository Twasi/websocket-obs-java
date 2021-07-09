package io.obswebsocket.community.client.message.event.filters;

import io.obswebsocket.community.client.message.event.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SourceFilterListReindexedEvent extends Event {

  private Data eventData;

  protected SourceFilterListReindexedEvent() {
    super(Type.SourceFilterListReindexed, Category.Filters);
  }

  @Getter
  @ToString
  public static class Data {
    // TODO SourceFilterListReindexedEvent.Data
  }
}
