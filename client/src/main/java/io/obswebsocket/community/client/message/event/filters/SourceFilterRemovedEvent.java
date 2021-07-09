package io.obswebsocket.community.client.message.event.filters;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SourceFilterRemovedEvent extends SourceFilterEvent {

  private Data eventData;

  protected SourceFilterRemovedEvent() {
    super(Type.SourceFilterRemoved, Category.Filters);
  }
}
