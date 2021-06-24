package io.obswebsocket.community.message.event.mediainputs;

import lombok.Getter;
import lombok.ToString;
import io.obswebsocket.community.message.event.Event;

@Getter
@ToString(callSuper = true)
abstract class MediaInputEvent extends Event {
    protected MediaInputEvent(Type eventType, Category category) {
        super(eventType, category);
    }

    @Getter
    @ToString
    protected static class Data {
        private String inputName;
    }
}
