package net.twasi.obsremotejava;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import net.twasi.obsremotejava.authenticator.Authenticator;
import net.twasi.obsremotejava.listener.lifecycle.ReasonThrowable;
import net.twasi.obsremotejava.listener.lifecycle.communicator.CommunicatorLifecycleListener;
import net.twasi.obsremotejava.listener.lifecycle.communicator.CommunicatorLifecycleListener.CodeReason;
import net.twasi.obsremotejava.message.Message;
import net.twasi.obsremotejava.message.authentication.Hello;
import net.twasi.obsremotejava.message.authentication.Identified;
import net.twasi.obsremotejava.message.authentication.Identify;
import net.twasi.obsremotejava.message.event.Event;
import net.twasi.obsremotejava.message.request.Request;
import net.twasi.obsremotejava.message.request.RequestBatch;
import net.twasi.obsremotejava.message.request.general.GetVersionRequest;
import net.twasi.obsremotejava.message.response.RequestBatchResponse;
import net.twasi.obsremotejava.message.response.RequestResponse;
import net.twasi.obsremotejava.message.response.general.GetVersionResponse;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@WebSocket(maxTextMessageSize = 1024 * 1024, maxIdleTime = 360000000)
public class OBSCommunicator {

    private final CountDownLatch closeLatch = new CountDownLatch(1);

    private final Gson gson;
    private final Authenticator authenticator;

    private final ConcurrentHashMap<Class<? extends Event>, Consumer> eventListeners;
    private final ConcurrentHashMap<String, Consumer> requestListeners = new ConcurrentHashMap<>();


    private Session session;

    private final CommunicatorLifecycleListener communicatorLifecycleListener;

    /**
     * All-args constructor used by the builder class.
     *
     * @param gson GSON instance
     * @param authenticator Authenticator instance; NoOpAuthenticator if no password, otherwise AuthenticatorImpl.
     * @param communicatorLifecycleListener {@link CommunicatorLifecycleListener}
     * @param eventListeners ConcurrentHashMap&lt;Class&lt;? extends {@link Event}&gt;, Consumer&gt;
     */
    public OBSCommunicator(
            Gson gson,
            Authenticator authenticator,
            CommunicatorLifecycleListener communicatorLifecycleListener,
            ConcurrentHashMap<Class<? extends Event>, Consumer> eventListeners) {
        this.gson = gson;
        this.authenticator = authenticator;
        this.communicatorLifecycleListener = communicatorLifecycleListener;
        this.eventListeners = eventListeners == null ? new ConcurrentHashMap<>() : eventListeners;
    }

    public static ObsCommunicatorBuilder builder() {
        return new ObsCommunicatorBuilder();
    }

    /**
     * Internal way of computing the eventSubscription
     *
     * @return int eventSubscription value according to registered {@link Event} listeners
     */
    private int computeEventSubscription() {
        return this.eventListeners.keySet().stream().map(aClass -> {
            Event.Category category = Event.Category.None;
            try {
                Constructor<? extends Event> constructor = aClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Event instance = constructor.newInstance();
                category = instance.getCategory();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return category;
        }).mapToInt(Event.Category::getValue).reduce(Event.Category.None.getValue(), (a, b) -> a | b);
    }

    /**
     * TODO: add awaitClose description
     *
     * @param duration int
     * @param unit TimeUnit
     * @return true if the count reached zero and false if the waiting time elapsed before the count reached zero
     * @throws InterruptedException
     */
    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    /**
     * TODO: Add await description
     *
     * @throws InterruptedException
     */
    public void await() throws InterruptedException {
        this.closeLatch.await();
    }

    @OnWebSocketError
    public void onError(Session session, Throwable t) {
        this.communicatorLifecycleListener
          .onError(this, new ReasonThrowable(
            "Websocket error occurred with session " + session, t
          ));
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.communicatorLifecycleListener.onClose(this, new CodeReason(statusCode, reason));
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        try {
            log.info("Connected to OBS at: " + this.session.getRemoteAddress());
            this.communicatorLifecycleListener.onConnect(this, this.session);
        } catch (Throwable t) {
            this.communicatorLifecycleListener.onError(this, new ReasonThrowable(
              "An error occurred while trying to get a session", t
            ));
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        log.debug("Received message <<\n" + msg);
        if (msg == null) {
            log.debug("Ignored empty message");
            return;
        }

        try {
            Message message = this.gson.fromJson(msg, Message.class);
            if (message != null) {
                switch (message.getMessageType()) {
                    case Event:
                        this.onEvent((Event) message);
                        break;

                    case RequestResponse:
                        this.onRequestResponse((RequestResponse) message);
                        break;

                    case RequestBatchResponse:
                        this.onRequestBatchResponse((RequestBatchResponse) message);
                        break;

                    case Hello:
                        this.onHello((Hello) message);
                        break;

                    case Identified:
                        this.onIdentified((Identified) message);
                        break;

                    default:
                        this.communicatorLifecycleListener.onError(this, new ReasonThrowable(
                          "Invalid response type received", null
                        ));
                }
            }
            else {
                this.communicatorLifecycleListener
                  .onError(this, new ReasonThrowable(
                    "Received message was deserializable but had unknown format", null
                  ));
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            this.communicatorLifecycleListener
              .onError(this, new ReasonThrowable(
                "Message received was not valid json: " + msg, jsonSyntaxException
              ));
        } catch (Throwable t) {
            this.communicatorLifecycleListener
              .onError(this, new ReasonThrowable(
                "Failed to process message from websocket due to unexpected exception", t
              ));
        }
    }

    /**
     * Internal callback when a {@link Event} is received
     *
     * @param event {@link Event}
     */
    private void onEvent(Event event) {
        try {
            if (this.eventListeners.containsKey(event.getClass())) {
                this.eventListeners.get(event.getClass()).accept(event);
            }
        } catch (Throwable t) {
            this.communicatorLifecycleListener
              .onError(this, new ReasonThrowable(
                "Failed to execute callback for Event: " + event.getEventType(), t
              ));
        }
    }

    /**
     * Internal callback when a {@link RequestResponse} is received
     *
     * @param requestResponse {@link RequestResponse}
     */
    private void onRequestResponse(RequestResponse requestResponse) {
        try {
            if (this.requestListeners.containsKey(requestResponse.getRequestId())) {
                this.requestListeners.get(requestResponse.getRequestId()).accept(requestResponse);
            }
        } catch (Throwable t) {
            this.communicatorLifecycleListener.onError(this, new ReasonThrowable(
              "Failed to execute callback for RequestResponse: " + requestResponse.getRequestType(), t
            ));
        }
        finally {
            this.requestListeners.remove(requestResponse.getRequestId());
        }
    }

    /**
     * Internal callback when a {@link RequestBatchResponse} is received
     *
     * @param requestBatchResponse {@link RequestBatchResponse}
     */
    private void onRequestBatchResponse(RequestBatchResponse requestBatchResponse) {
        try {
            if (this.requestListeners.containsKey(requestBatchResponse.getRequestId())) {
                this.requestListeners.get(requestBatchResponse.getRequestId()).accept(requestBatchResponse);
            }
        } catch (Throwable t) {
            this.communicatorLifecycleListener.onError(this, new ReasonThrowable(
              "Failed to execute callback for RequestBatchResponse: " + requestBatchResponse, t
            ));
        }
        finally {
            this.requestListeners.remove(requestBatchResponse.getRequestId());
        }
    }

    /**
     * First response from server when reached; contains authentication info if required to connect.
     *
     * @param hello {@link Hello}
     */
    public void onHello(Hello hello) {

        log.debug(String.format(
          "Negotiated Rpc version %s. Authentication is required: %s",
          hello.getRpcVersion(),
          hello.isAuthenticationRequired()
        ));

        // Build the identify response
        Identify.IdentifyBuilder identifyBuilder = Identify.builder()
          .rpcVersion(hello.getRpcVersion());

        // Add subscription
        identifyBuilder.eventSubscriptions(this.computeEventSubscription());

        // Add authentication, if required
        if(hello.isAuthenticationRequired()) {
            // Build the authentication string
            String authentication = this.authenticator.computeAuthentication(
              hello.getAuthentication().getSalt(),
              hello.getAuthentication().getChallenge()
            );
            identifyBuilder.authentication(authentication);
        }

        // Send the response
        this.communicatorLifecycleListener.onHello(this, hello);
        this.sendMessage(identifyBuilder.build());
    }

    /**
     * Sent from server on successful authentication/connection
     *
     * @param identified {@link Identified}
     */
    public void onIdentified(Identified identified) {
        log.info("Identified by OBS, ready to accept requests");
        this.communicatorLifecycleListener.onIdentified(this, identified);

        this.sendRequest(new GetVersionRequest(), (GetVersionResponse getVersionResponse) -> {
            log.info(String.format("Using OBS %s and Websockets version %s",
                    getVersionResponse.getResponseData().getObsVersion(), getVersionResponse.getResponseData().getObsWebSocketVersion()));
        });
    }

    /**
     * An internal convenience method to centralize outbound calls to OBS
     * for e.g. logging purposes.
     *
     * @param message message to send (e.g. a JSON object)
     */
    private void send(String message) {
        log.debug("Sent message     >>\n" + message);
        this.session.getRemote().sendStringByFuture(message);
    }

    /**
     * Internal send Message
     *
     * @param message {@link Message}
     */
    private void sendMessage(Message message) {
        this.send(this.gson.toJson(message));
    }

    /**
     * Send a {@link Request} and register a {@link RequestResponse} callback
     *
     * @param request R
     * @param callback Consumer&lt;RR&gt;
     * @param <R> extends {@link Request}
     * @param <RR> extends {@link RequestResponse}
     */
    public <R extends Request, RR extends RequestResponse> void sendRequest(R request, Consumer<RR> callback) {
        this.requestListeners.put(request.getRequestId(), callback);
        this.sendMessage(request);
    }

    /**
     * Send a {@link RequestBatch} and register a {@link RequestBatchResponse} callback
     *
     * @param requestBatch {@link RequestBatch}
     * @param callback {@link RequestBatchResponse}
     */
    public void sendRequestBatch(RequestBatch requestBatch, Consumer<RequestBatchResponse> callback) {
        if (requestBatch.getRequests() != null && !requestBatch.getRequests().isEmpty()) {
            this.requestListeners.put(requestBatch.getRequestId(), callback);
            this.sendMessage(requestBatch);
        }
    }
}
