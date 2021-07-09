package io.obswebsocket.community.client.message.event.transitions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class TransitionRemovedEvent extends TransitionEvent {

  private Data eventData;

  protected TransitionRemovedEvent() {
    super(Type.TransitionRemoved, Category.Transitions);
  }
}
