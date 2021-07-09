package io.obswebsocket.community.client.message.event.transitions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CurrentTransitionChangedEvent extends TransitionEvent {

  private Data eventData;

  protected CurrentTransitionChangedEvent() {
    super(Type.CurrentTransitionChanged, Category.Transitions);
  }
}
