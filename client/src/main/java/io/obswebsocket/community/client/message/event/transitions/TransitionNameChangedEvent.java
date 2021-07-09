package io.obswebsocket.community.client.message.event.transitions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class TransitionNameChangedEvent extends TransitionEvent {

  private Data eventData;

  protected TransitionNameChangedEvent() {
    super(Type.TransitionNameChanged, Category.Transitions);
  }

  @Getter
  @ToString(callSuper = true)
  public static class Data extends TransitionEvent.Data {

    private String oldTransitionName; // TODO TransitionNameChangedEvent.Data
  }
}
