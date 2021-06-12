package net.twasi.obsremotejava.message.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.twasi.obsremotejava.message.Message;

@Getter
@ToString
public class Reidentify extends Message {
//    static {
//        Message.registerMessageType(Message.Type.Reidentify, Reidentify.class);
//    }

    private Boolean ignoreInvalidMessages;
    private Boolean ignoreNonFatalRequestChecks;
    private Integer eventSubscriptions;

//    private Reidentify() {
//        super(Type.Reidentify);
//    }

    @Builder
    public Reidentify(Boolean ignoreInvalidMessages,
      Boolean ignoreNonFatalRequestChecks, Integer eventSubscriptions) {
        super(Type.Reidentify);
        this.ignoreInvalidMessages = ignoreInvalidMessages;
        this.ignoreNonFatalRequestChecks = ignoreNonFatalRequestChecks;
        this.eventSubscriptions = eventSubscriptions;
    }

//    public static class Builder {
//        private Boolean ignoreInvalidMessages;
//        private Boolean ignoreNonFatalRequestChecks;
//        private Integer eventSubscriptions;
//
//        public Builder() {}
//
//        public Builder ignoreInvalidMessages(Boolean ignoreInvalidMessages) {
//            this.ignoreInvalidMessages = ignoreInvalidMessages;
//
//            return this;
//        }
//
//        public Builder ignoreNonFatalRequestChecks(Boolean ignoreNonFatalRequestChecks) {
//            this.ignoreNonFatalRequestChecks = ignoreNonFatalRequestChecks;
//
//            return this;
//        }
//
//        public Builder eventSubscriptions(Integer eventSubscriptions) {
//            this.eventSubscriptions = eventSubscriptions;
//
//            return this;
//        }
//
//        public Reidentify build() {
//            Reidentify reidentify = new Reidentify();
//            reidentify.ignoreInvalidMessages = this.ignoreInvalidMessages;
//            reidentify.ignoreNonFatalRequestChecks = this.ignoreNonFatalRequestChecks;
//            reidentify.eventSubscriptions = this.eventSubscriptions;
//
//            return reidentify;
//        }
//    }
}
