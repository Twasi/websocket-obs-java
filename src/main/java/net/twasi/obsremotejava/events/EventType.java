package net.twasi.obsremotejava.events;

public enum EventType {
    RecordingStarted,
    RecordingStopped,
    ReplayStarting,
    ReplayStarted,
    ReplayStopping,
    ReplayStopped,
    StreamStarted,
    StreamStopped,
    SwitchScenes,
    ScenesChanged,
    SourceFilterVisibilityChanged,
    SourceVolumeChanged,
    SwitchTransition,
    TransitionListChanged,
    TransitionBegin,
    TransitionEnd,
    PreviewSceneChanged,
    MediaPlaying,
    MediaPaused,
    MediaRestarted,
    MediaStopped,
    MediaNext,
    MediaPrevious,
    MediaStarted,
    MediaEnded
}
