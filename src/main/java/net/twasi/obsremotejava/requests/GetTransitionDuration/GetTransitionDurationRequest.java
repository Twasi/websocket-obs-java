package net.twasi.obsremotejava.requests.GetTransitionDuration;

import net.twasi.obsremotejava.OBSCommunicator;
import net.twasi.obsremotejava.requests.RequestBase;
import net.twasi.obsremotejava.requests.RequestType;

public class GetTransitionDurationRequest extends RequestBase {
    public GetTransitionDurationRequest(OBSCommunicator com) {
        super(RequestType.GetTransitionDuration);

        com.messageTypes.put(getMessageId(), GetTransitionDurationResponse.class);
    }
}
