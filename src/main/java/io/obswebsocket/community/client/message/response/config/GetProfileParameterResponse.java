package io.obswebsocket.community.client.message.response.config;

import lombok.Getter;
import lombok.ToString;
import io.obswebsocket.community.client.message.request.Request;
import io.obswebsocket.community.client.message.response.RequestResponse;

@Getter
@ToString(callSuper = true)
public class GetProfileParameterResponse extends RequestResponse {
    private Data responseData;

    public GetProfileParameterResponse() {
        super(Request.Type.GetProfileParameter);
    }

    @Getter
    @ToString
    public static class Data {
        private String parameterValue;
        private String defaultParameterValue;
    }
}