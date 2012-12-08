package springWeb.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import springWeb.domain.ChatResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChatEngineRemoteTest {
    public static final int PORT = 9000;
    public static final String SERVER_URL = "http://localhost:";
    public static final String DATE_TIME_TEMPLATE = "yyyy-MM-dd hh:mm:ss";
    public static final String ANSWER = "answer content";
    public static final String CONTEXT = "context content";
    private Server server;
    private HttpRequestHandler httpHandler;
    private SimpleDateFormat fullDateTimeFormat;

    @Before
    public void setUp() throws Exception {
        server = new Server(PORT);
        httpHandler = new ChatServerHandler(ANSWER, CONTEXT);
        server.setHandler(httpHandler);
        server.start();
        fullDateTimeFormat = new SimpleDateFormat(DATE_TIME_TEMPLATE);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void should_send_chat_info_to_chat_server() {
        ChatEngineRemote chatEngineRemote = new ChatEngineRemote();
        chatEngineRemote.setChatServerUrl(SERVER_URL + PORT);
        ChatResponse chatResponse = chatEngineRemote.chatWithEngine1("question", "context");
        assertThat(chatResponse.getAnswer(), is(ANSWER));
        assertThat(chatResponse.getContext(), is(CONTEXT));
    }
}
