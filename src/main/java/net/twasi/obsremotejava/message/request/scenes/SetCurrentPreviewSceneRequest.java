package net.twasi.obsremotejava.message.request.scenes;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import net.twasi.obsremotejava.message.request.Request;

@Getter
@ToString(callSuper = true)
public class SetCurrentPreviewSceneRequest extends Request {
    private final Data requestData;

    public SetCurrentPreviewSceneRequest(String sceneName) {
        super(Type.SetCurrentPreviewScene);

        this.requestData = Data.builder().sceneName(sceneName).build();
    }

    @Getter
    @ToString
    @Builder
    static class Data {
        @NonNull
        private final String sceneName;
    }
}
