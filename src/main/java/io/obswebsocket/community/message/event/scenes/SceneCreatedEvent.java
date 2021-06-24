package io.obswebsocket.community.message.event.scenes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SceneCreatedEvent extends SceneChangedEvent {
    protected SceneCreatedEvent() {
        super(Type.SceneCreated, Category.Scenes);
    }
}
