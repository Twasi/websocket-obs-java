package io.obswebsocket.community.client.message.event.filters;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class FilterNameChangedEvent extends FilterEvent {

  private Data eventData;

  protected FilterNameChangedEvent() {
    super(Type.FilterNameChanged, Category.Filters);
  }

  @Getter
  @ToString(callSuper = true)
  public static class Data extends FilterEvent.Data {

    private String oldFilterName;
  }
}
