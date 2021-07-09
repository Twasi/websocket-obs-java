package io.obswebsocket.community.client.message.event.transitions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class TransitionEndedEvent extends TransitionEvent {

  private Data eventData;

  protected TransitionEndedEvent() {
    super(Type.TransitionEnded, Category.Transitions);
  }
}
