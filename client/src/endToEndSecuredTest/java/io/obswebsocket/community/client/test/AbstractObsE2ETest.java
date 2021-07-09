package io.obswebsocket.community.client.test;

import static org.assertj.core.api.Assertions.assertThat;

import io.obswebsocket.community.client.OBSRemoteController;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public abstract class AbstractObsE2ETest {

  protected static OBSRemoteController remote;
  protected BlockingQueue resultQueue = new LinkedBlockingQueue();

  // Scenes
  protected final static String SCENE1 = "scene1";
  protected final static String SCENE2 = "scene2";
  protected final static String SCENE3 = "emptyscene";

  // Sources
  protected final static String SOURCE_TEXT_SCENE1 = "scenename1";
  protected final static String SOURCE_TEXT_SCENE2 = "scenename2";
  protected final static String SOURCE_MEDIA = "media";
  protected final static String SOURCE_VLC_MEDIA = "vlc-media";
  protected final static String SOURCE_RED_SQUARE = "red_square";
  protected final static String SOURCE_RED_SQUARE_FILTER = "Color Correction";
  protected final static String SOURCE_BROWSER = "browser";
  protected final static String SOURCE_GROUP = "group";
  protected final static String SOURCE_GROUP_TEXT = "grouptext";

  // Transitions and other OBS hard-coded names
  protected final static String TRANSITION_SLIDE = "Slide";
  protected final static String TRANSITION_CUT = "Cut";
  protected final static String TRANSITION_FADE = "Fade";
  protected final static String SOURCE_OBS_MIC = "Mic/Aux";
  protected final static String SOURCE_OBS_AUDIO = "Desktop Audio";

  // Test Helpers
  protected void obsShould(String expected) {
    obsShould(expected, 3);
  }

  protected void obsShould(String expected, int secondsTimeout) {
    System.out.println(">>> OBS SHOULD: " + expected);
    countDownFrom(secondsTimeout);
  }

  protected void countDownFrom(int seconds) {
    for (int i = seconds; i > 0; i--) {
      System.out.println("> " + i);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  protected static void connectToObs() {
    remote = OBSRemoteController.builder()
        .lifecycle()
        .onControllerError(((reasonThrowable) -> {
          System.out.println("An error occurred: " + reasonThrowable.getReason());
          reasonThrowable.getThrowable().printStackTrace();
        }))
        .and()
        .build();
//    remote = new OBSRemoteController("ws://localhost:4444", false);
//    remote.registerConnectionFailedCallback(message -> {
//      fail("Failed to connect to OBS: " + message);
//    });
//    remote.registerOnError((message, throwable) -> {
//      fail("Failed to connect to OBS due to error: " + message);
//    });
    remote.connect();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  protected void setupObs() {

    // Cleanup all scenes
    cleanupScenes();

    // Change back to base scene
//    remote.changeSceneWithTransition("scene1", "Cut", result -> {
//      if(result.getError() != null && !result.getError().isEmpty()) {
//        fail("Failed to switch to base scene");
//      }
//    });
  }

  protected void cleanupScenes() {
//    // Hide all visible elements in all scenes
//    remote.getSceneList(sceneListResponse -> {
//      sceneListResponse.getScenes().forEach(scene -> {
//        scene.getSources().forEach(source -> {
//          if(!source.getName().startsWith("scenename")) {
//            remote.setSourceVisibility(scene.getName(), source.getName(), false, result -> {
//              if(result.getError() != null && !result.getError().isEmpty()) {
//                fail(String.format("Failed to hide source '%s' on scene '%s'", source.getName(), scene.getName()));
//              }
//            });
//          }
//        });
//      });
//    });
  }

  protected Consumer loggingCallback = (obj) -> {
    System.out.println("Received response: " + obj);
  };

  protected void waitReasonably() {
    waitReasonably(50);
  }

  protected void waitReasonably(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  protected Consumer capturingCallback = (obj) -> {
    System.out.println("Received response: " + obj + "(" + obj.getClass().getSimpleName() + ")");
    resultQueue.add(obj);
  };

  protected <T> T getPreviousResponseAs(Class<T> clazz) {
    waitReasonably();
    Object previousResponse = resultQueue.remove();
    assertThat(previousResponse).isInstanceOf(clazz);
    return clazz.cast(previousResponse);
  }

}
