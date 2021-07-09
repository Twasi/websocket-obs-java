package io.obswebsocket.community.client.message.event.scenes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CurrentSceneChangedEvent extends SceneEvent {

  private Data eventData;

  protected CurrentSceneChangedEvent() {
    super(Type.CurrentSceneChanged, Category.Scenes);
  }
}
