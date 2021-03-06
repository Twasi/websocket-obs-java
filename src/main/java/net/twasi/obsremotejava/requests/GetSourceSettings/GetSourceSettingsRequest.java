package net.twasi.obsremotejava.requests.GetSourceSettings;

import net.twasi.obsremotejava.OBSCommunicator;
import net.twasi.obsremotejava.requests.RequestBase;
import net.twasi.obsremotejava.requests.RequestType;

public class GetSourceSettingsRequest extends RequestBase {
    private String sourceName;

    public GetSourceSettingsRequest(OBSCommunicator com, String sourceName) {
        super(RequestType.GetSourceSettings);

        this.sourceName = sourceName;

        com.messageTypes.put(getMessageId(), GetSourceSettingsResponse.class);
    }
}
