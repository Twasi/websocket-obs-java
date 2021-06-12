package net.twasi.obsremotejava.message.event.media;

import lombok.Getter;
import lombok.ToString;
import net.twasi.obsremotejava.message.event.Event;

@Getter
@ToString
public abstract class Media extends Event {
    private Data eventData;

    protected Media(Type eventType, Category category) {
        super(eventType, category);
    }

    public static class Data {
        private String inputName;
    }
}
