package com.technokratos.adboard.websocket;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import com.technokratos.adboard.dto.request.ChatMessageRequest;
import com.technokratos.adboard.dto.response.ChatNotification;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import static com.technokratos.adboard.constant.Constant.BEARER;
import static com.technokratos.adboard.constant.Constant.DESTINATION_PREFIX;
import static com.technokratos.adboard.constant.Constant.MESSAGE_DESTINATION;
import static com.technokratos.adboard.constant.Constant.SEND_MESSAGE_ENDPOINT;
import static com.technokratos.adboard.constant.TestConstant.FIRST_USER;
import static com.technokratos.adboard.constant.TestConstant.SECOND_USER_ID;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author d.gilfanova
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = {"classpath:application-test.yml"})
@ContextConfiguration(initializers = {
    TestPostgresContainer.PropertiesInitializer.class,
    TestMinioContainer.PropertiesInitializer.class})
@Sql(scripts = "/db/chat_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean_chat_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class WebSocketControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String URL;

    private CompletableFuture<ChatNotification> completableFuture;

    @BeforeEach
    public void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://127.0.0.1:" + port + "/ws";
    }

    @Test
    public void test_send_message_endpoint() throws InterruptedException, ExecutionException,
        TimeoutException {
        WebSocketStompClient stompClient = createStompClient();

        String userAuthToken = BEARER.concat(jwtTokenProvider.generateAccessToken(FIRST_USER.getEmail(),
            Collections.singletonMap("ROLE", FIRST_USER.getRole())));

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add(AUTHORIZATION, userAuthToken);

        //connect
        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(),
                stompHeaders, new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);

        //subscribe on interlocutor destination
        stompSession.subscribe(DESTINATION_PREFIX + "/" + SECOND_USER_ID
                               + MESSAGE_DESTINATION, new messageStompFrameHandler());

        //send message with destination header
        stompHeaders.add(StompHeaders.DESTINATION, SEND_MESSAGE_ENDPOINT);

        stompSession.send(stompHeaders, ChatMessageRequest.builder()
                                          .content("hello")
                                          .recipientId(UUID.fromString(SECOND_USER_ID))
                                          .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                                          .build());

        ChatNotification chatNotification = completableFuture.get(10, SECONDS);

        Assertions.assertNotNull(chatNotification);
        Assertions.assertEquals(chatNotification.getSenderEmail(), FIRST_USER.getEmail());
    }

    @Test
    public void get_exception_when_connect_without_token() {
        WebSocketStompClient stompClient = createStompClient();

        assertThrows(ExecutionException.class, () -> {
            stompClient.connect(URL, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        });
    }

    @Test
    public void get_exception_when_send_without_token() throws InterruptedException, ExecutionException,
        TimeoutException {
        WebSocketStompClient stompClient = createStompClient();

        String userAuthToken = BEARER.concat(jwtTokenProvider.generateAccessToken(FIRST_USER.getEmail(),
            Collections.singletonMap("ROLE", FIRST_USER.getRole())));

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add(AUTHORIZATION, userAuthToken);

        //connect
        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(),
                stompHeaders, new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);

        //subscribe on interlocutor destination
        stompSession.subscribe(DESTINATION_PREFIX + "/" + SECOND_USER_ID
                               + MESSAGE_DESTINATION, new messageStompFrameHandler());

        //send message without auth header
        stompSession.send(SEND_MESSAGE_ENDPOINT, ChatMessageRequest.builder()
            .content("hello")
            .recipientId(UUID.fromString(SECOND_USER_ID))
            .timestamp(Timestamp.valueOf(LocalDateTime.now()))
            .build());

        assertThrows(TimeoutException.class, () -> {
            completableFuture.get(10, SECONDS);
        });
    }

    @Test
    public void get_exception_when_send_invalid_data() throws InterruptedException, ExecutionException,
        TimeoutException {
        WebSocketStompClient stompClient = createStompClient();

        String userAuthToken = BEARER.concat(jwtTokenProvider.generateAccessToken(FIRST_USER.getEmail(),
            Collections.singletonMap("ROLE", FIRST_USER.getRole())));

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add(AUTHORIZATION, userAuthToken);

        //connect
        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(),
                stompHeaders, new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);

        //subscribe on interlocutor destination
        stompSession.subscribe(DESTINATION_PREFIX + "/" + SECOND_USER_ID
                               + MESSAGE_DESTINATION, new messageStompFrameHandler());

        //send message with destination header
        stompHeaders.add(StompHeaders.DESTINATION, SEND_MESSAGE_ENDPOINT);

        stompSession.send(stompHeaders, ChatMessageRequest.builder()
            .content(StringUtils.EMPTY)
            .recipientId(UUID.fromString(SECOND_USER_ID))
            .timestamp(Timestamp.valueOf(LocalDateTime.now()))
            .build());

        assertThrows(TimeoutException.class, () -> {
            completableFuture.get(10, SECONDS);
        });
    }

    private WebSocketStompClient createStompClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(
            new SockJsClient(List.of(
                new WebSocketTransport(
                    new StandardWebSocketClient()))));

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);

        return stompClient;
    }

    private class messageStompFrameHandler implements StompFrameHandler {
        @Override
        public @NotNull Type getPayloadType(@NotNull StompHeaders stompHeaders) {
            return ChatNotification.class;
        }

        @Override
        public void handleFrame(@NotNull StompHeaders stompHeaders, Object o) {
            completableFuture.complete((ChatNotification) o);
        }
    }
}
