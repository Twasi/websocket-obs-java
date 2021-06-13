package net.twasi.obsremotejava.message.request;

import java.util.UUID;

import lombok.Getter;
import lombok.ToString;
import net.twasi.obsremotejava.message.Message;
import net.twasi.obsremotejava.message.request.config.CreateSceneCollectionRequest;
import net.twasi.obsremotejava.message.request.config.DeleteSceneCollectionRequest;
import net.twasi.obsremotejava.message.request.config.GetSceneCollectionListRequest;
import net.twasi.obsremotejava.message.request.config.SetCurrentSceneCollectionRequest;
import net.twasi.obsremotejava.message.request.general.*;
import net.twasi.obsremotejava.message.request.scenes.GetSceneListRequest;
import net.twasi.obsremotejava.message.response.RequestResponse;
import net.twasi.obsremotejava.message.response.config.CreateSceneCollectionResponse;
import net.twasi.obsremotejava.message.response.config.DeleteSceneCollectionResponse;
import net.twasi.obsremotejava.message.response.config.GetSceneCollectionListResponse;
import net.twasi.obsremotejava.message.response.config.SetCurrentSceneCollectionResponse;
import net.twasi.obsremotejava.message.response.general.*;
import net.twasi.obsremotejava.message.response.scenes.GetSceneListResponse;

@Getter
@ToString(callSuper = true)
public abstract class Request extends Message {
    protected Type requestType;
    protected String requestId;

    public Request(Type type) {
        super(Message.Type.Request);

        this.requestType = type;
        this.requestId = UUID.randomUUID().toString();
    }

    @Getter
    public enum Type {
        // General
        GetVersion(GetVersionRequest.class, GetVersionResponse.class),
        BroadcastCustomEvent(BroadcastCustomEventRequest.class, BroadcastCustomEventResponse.class),
        GetHotkeyList(GetHotkeyListRequest.class, GetHotkeyListResponse.class),
        TriggerHotkeyByName(TriggerHotkeyByNameRequest.class, TriggerHotkeyByNameResponse.class),
        TriggerHotkeyByKeySequence(TriggerHotkeyByKeySequenceRequest.class, TriggerHotkeyByKeySequenceResponse.class),
        GetStudioModeEnabled(GetStudioModeEnabledRequest.class, GetStudioModeEnabledResponse.class),
        SetStudioModeEnabled(SetStudioModeEnabledRequest.class, SetStudioModeEnabledResponse.class),
        Sleep(SleepRequest.class, SleepResponse.class),

        // Config
        GetSceneCollectionList(GetSceneCollectionListRequest.class, GetSceneCollectionListResponse.class),
        SetCurrentSceneCollection(SetCurrentSceneCollectionRequest.class, SetCurrentSceneCollectionResponse.class),
        CreateSceneCollection(CreateSceneCollectionRequest.class, CreateSceneCollectionResponse.class),
        DeleteSceneCollection(DeleteSceneCollectionRequest.class, DeleteSceneCollectionResponse.class),

        // Scenes
        GetSceneList(GetSceneListRequest.class, GetSceneListResponse.class),
        ;

        private final Class<? extends Request> requestClass;
        private final Class<? extends RequestResponse> requestResponseClass;

        Type(Class<? extends Request> requestClass, Class<? extends RequestResponse> requestResponseClass) {
            this.requestClass = requestClass;
            this.requestResponseClass = requestResponseClass;
        }
    }
}