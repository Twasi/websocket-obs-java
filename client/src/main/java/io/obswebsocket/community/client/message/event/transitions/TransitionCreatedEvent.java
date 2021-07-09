package io.obswebsocket.community.client.message.event.transitions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class TransitionCreatedEvent extends TransitionEvent {

  private Data eventData;

  protected TransitionCreatedEvent() {
    super(Type.TransitionCreated, Category.Transitions);
  }
}
