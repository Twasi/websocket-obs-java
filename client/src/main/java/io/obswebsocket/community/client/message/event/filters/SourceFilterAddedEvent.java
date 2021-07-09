package io.obswebsocket.community.client.message.event.filters;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SourceFilterAddedEvent extends SourceFilterEvent {

  private Data eventData;

  protected SourceFilterAddedEvent() {
    super(Type.SourceFilterAdded, Category.Filters);
  }
}
