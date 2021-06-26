package io.obswebsocket.community.client.message.event.scenes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CurrentSceneChangedEvent extends SceneChangedEvent {
    protected CurrentSceneChangedEvent() {
        super(Type.CurrentSceneChanged, Category.Scenes);
    }
}
