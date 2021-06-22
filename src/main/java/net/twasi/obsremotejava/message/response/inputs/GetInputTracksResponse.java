package net.twasi.obsremotejava.message.response.inputs;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import net.twasi.obsremotejava.message.request.Request;
import net.twasi.obsremotejava.message.response.RequestResponse;

@Getter
@ToString(callSuper = true)
public class GetInputTracksResponse extends RequestResponse {
    private Data responseData;

    public GetInputTracksResponse() {
        super(Request.Type.GetInputTracks);
    }

    @Getter
    @ToString
    public static class Data {
        private JsonObject inputSettings;
    }
}
