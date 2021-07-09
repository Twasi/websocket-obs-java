package io.obswebsocket.community.client.message.event.filters;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class FilterCreatedEvent extends FilterEvent {

  private Data eventData;

  protected FilterCreatedEvent() {
    super(Type.FilterCreated, Category.Filters);
  }
}
