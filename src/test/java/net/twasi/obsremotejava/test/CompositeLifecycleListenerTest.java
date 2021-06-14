package net.twasi.obsremotejava.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import net.twasi.obsremotejava.OBSCommunicator;
import net.twasi.obsremotejava.ObsCommunicatorBuilder;
import net.twasi.obsremotejava.listener.lifecycle.CompositeLifecycleListener;
import net.twasi.obsremotejava.listener.lifecycle.LifecycleListener;
import net.twasi.obsremotejava.listener.lifecycle.LifecycleListenerBuilder;
import net.twasi.obsremotejava.message.authentication.Hello;
import net.twasi.obsremotejava.message.authentication.Identified;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class CompositeLifecycleListenerTest {

  @Test
  void allListenersAreCalled() {
    // Given some listeners registered to a composite listener
    LifecycleListener lifecycleListener1 = mock(LifecycleListener.class);
    LifecycleListener lifecycleListener2 = mock(LifecycleListener.class);
    List<LifecycleListener> listeners = Arrays.asList(
      lifecycleListener1, lifecycleListener2
    );

    LifecycleListener compositeListener = new CompositeLifecycleListener(listeners);

    // When called
    compositeListener.onConnect(mock(Session.class));
    compositeListener.onHello(mock(Hello.class));
    compositeListener.onIdentified(mock(OBSCommunicator.class), mock(Identified.class));
    compositeListener.onClose(42, "foo");
    compositeListener.onError("bar", mock(Throwable.class));

    // Then each is called
    listeners.forEach(listener -> {
      verify(listener).onConnect(any());
      verify(listener).onHello(any());
      verify(listener).onIdentified(any(), any());
      verify(listener).onClose(any(), any());
      verify(listener).onError(any(), any());
    });

  }

  @Test
  void lifecycleListenerBuilderProvidesCompositeListener() {
    assertThat(new LifecycleListenerBuilder(new ObsCommunicatorBuilder()).build())
      .isInstanceOf(CompositeLifecycleListener.class);
  }
}