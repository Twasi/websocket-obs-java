package io.obswebsocket.community.client.test.translator.requestSerializationTests;

import io.obswebsocket.community.client.message.request.scenes.CreateSceneRequest;
import io.obswebsocket.community.client.message.request.scenes.DeleteSceneTransitionOverrideRequest;
import io.obswebsocket.community.client.test.translator.AbstractSerializationTest;
import org.junit.jupiter.api.Test;

public class SceneRequestsSerializationTest extends AbstractSerializationTest {
    @Test
    void createSceneRequest() {
        CreateSceneRequest createSceneRequest = CreateSceneRequest.builder()
                .sceneName("Scene name")
                .build();

        String json = "{\n" +
                "\t\"requestData\": {\n" +
                "\t\t\"sceneName\": \"Scene name\"\n" +
                "\t},\n" +
                "\t\"requestType\": \"CreateScene\",\n" +
                "\t\"requestId\": " + createSceneRequest.getRequestId() + ",\n" +
                "\t\"messageType\": \"Request\"\n" +
                "}";

        assertSerializationAndDeserialization(json, createSceneRequest);
    }

    @Test
    void deleteSceneTransitionOverrideRequest() {
        DeleteSceneTransitionOverrideRequest deleteSceneTransitionOverrideRequest = DeleteSceneTransitionOverrideRequest.builder()
                .sceneName("Scene name")
                .build();

        String json = "{\n" +
                "\t\"requestData\": {\n" +
                "\t\t\"sceneName\": \"Scene name\"\n" +
                "\t},\n" +
                "\t\"requestType\": \"DeleteSceneTransitionOverride\",\n" +
                "\t\"requestId\": " + deleteSceneTransitionOverrideRequest.getRequestId() + ",\n" +
                "\t\"messageType\": \"Request\"\n" +
                "}";

        assertSerializationAndDeserialization(json, deleteSceneTransitionOverrideRequest);
    }
}