package io.obswebsocket.community.client.test.message;

import static org.assertj.core.api.Assertions.assertThat;

import io.obswebsocket.community.client.OBSRemoteController;
import io.obswebsocket.community.client.message.response.general.GetVersionResponse;
import io.obswebsocket.community.client.test.AbstractObsE2ETest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneralE2eIT extends AbstractObsE2ETest {

  @BeforeEach
  void setUp() throws Exception {
    // Start and block on connect, to ensure test runs as expected
    CompletableFuture<Void> ready = new CompletableFuture();
    remote = OBSRemoteController.builder()
      .password("password")
      .lifecycle()
        .onReady(() -> ready.complete(null))
        .and()
      .build();
    remote.connect();
    ready.get();
  }

  @AfterEach
  void tearDown() {
    remote.disconnect();
  }

  @Test
  void getVersion() {
    remote.getVersion(capturingCallback);

    GetVersionResponse response = getPreviousResponseAs(GetVersionResponse.class);
    assertThat(response).isNotNull();
    assertThat(response.getResponseData().getObsVersion()).isNotEmpty();
    assertThat(response.getResponseData().getObsWebSocketVersion()).isNotEmpty();
    assertThat(response.getResponseData().getRpcVersion()).isPositive();
    assertThat(response.getResponseData().getSupportedImageFormats()).isNotEmpty();
    assertThat(response.getResponseData().getAvailableRequests()).isNotEmpty();

  }


}
