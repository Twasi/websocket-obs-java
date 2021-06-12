package net.twasi.obsremotejava.message.event.outputs;

import lombok.Getter;
import lombok.ToString;
import net.twasi.obsremotejava.message.event.Event;

@Getter
@ToString
public abstract class OutputStateChanged extends Event {
    private Data eventData;

    protected OutputStateChanged(Type eventType, Category category) {
        super(eventType, category);
    }

    @Getter
    @ToString
    public static class Data {
        private Boolean outputActive;
        private String outputState;
    }
}
