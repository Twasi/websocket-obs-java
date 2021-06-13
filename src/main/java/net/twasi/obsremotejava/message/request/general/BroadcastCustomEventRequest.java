package net.twasi.obsremotejava.message.request.general;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import net.twasi.obsremotejava.message.request.Request;

@Getter
@ToString(callSuper = true)
public class BroadcastCustomEventRequest extends Request {
    private final JsonObject requestData;

    public BroadcastCustomEventRequest(JsonObject requestData) {
        super(Type.BroadcastCustomEvent);

        this.requestData = requestData;
    }
}
