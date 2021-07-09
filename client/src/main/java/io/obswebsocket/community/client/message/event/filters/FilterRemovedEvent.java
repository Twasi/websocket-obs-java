package io.obswebsocket.community.client.message.event.filters;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class FilterRemovedEvent extends FilterEvent {

  private Data eventData;

  protected FilterRemovedEvent() {
    super(Type.FilterRemoved, Category.Filters);
  }
}
