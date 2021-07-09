package io.obswebsocket.community.client.message.event.scenes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SceneRemovedEvent extends SceneEvent {

  private Data eventData;

  protected SceneRemovedEvent() {
    super(Type.SceneRemoved, Category.Scenes);
  }

  @Getter
  @ToString(callSuper = true)
  public static class Data extends SceneEvent.Data {
    private Boolean isGroup;
  }
}
