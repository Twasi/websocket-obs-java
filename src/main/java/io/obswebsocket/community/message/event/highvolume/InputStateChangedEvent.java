package io.obswebsocket.community.message.event.highvolume;

import lombok.Getter;
import lombok.ToString;
import io.obswebsocket.community.message.event.Event;

@Getter
@ToString(callSuper = true)
abstract class InputStateChangedEvent extends Event {
    protected InputStateChangedEvent(Type eventType, Category category) {
        super(eventType, category);
    }

    @Getter
    @ToString
    protected static class Data {
        private String inputName;
    }
}