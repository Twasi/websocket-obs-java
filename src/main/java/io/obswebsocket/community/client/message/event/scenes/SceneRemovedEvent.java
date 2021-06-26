package io.obswebsocket.community.client.message.event.scenes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SceneRemovedEvent extends SceneChangedEvent {
    protected SceneRemovedEvent() {
        super(Type.SceneRemoved, Category.Scenes);
    }
}
