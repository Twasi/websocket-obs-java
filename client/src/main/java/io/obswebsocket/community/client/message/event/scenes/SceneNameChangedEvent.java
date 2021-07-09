package io.obswebsocket.community.client.message.event.scenes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SceneNameChangedEvent extends SceneEvent {

  private Data eventData;

  protected SceneNameChangedEvent() {
    super(Type.SceneNameChanged, Category.Scenes);
  }

  @Getter
  @ToString(callSuper = true)
  public static class Data extends SceneEvent.Data {

    private String oldSceneName;
  }
}
