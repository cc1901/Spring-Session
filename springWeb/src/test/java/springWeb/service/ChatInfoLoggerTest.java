package springWeb.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.springframework.mock.web.MockHttpServletRequest;
import springWeb.util.FullDateTimeFormator;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChatInfoLoggerTest {

    public static final int PORT = 9000;
    public static final String SERVER_URL = "http://localhost:";
    public static final String DATE_TIME_TEMPLATE = "yyyy-MM-dd hh:mm:ss";
    private Server server;
    private HttpRequestHandler httpHandler;
    private SimpleDateFormat fullDateTimeFormat;

    @Before
    public void setUp() throws Exception {
        server = new Server(PORT);
        httpHandler = new HttpRequestHandler();
        server.setHandler(httpHandler);
        server.start();
        fullDateTimeFormat = new SimpleDateFormat(DATE_TIME_TEMPLATE);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void should_send_user_info_to_log_server() {
        String clientIp = "10.10.10.10";
        String sessionId = "sessionId";
        String question = "questionContent";
        String answer = "answerContent";
        Date currentDate = new Date();
        HttpServletRequest request = createRequest(clientIp, sessionId);
        new ChatInfoLogger(SERVER_URL + PORT).logChatHistoryInfo(request.getRequestedSessionId(), request.getRemoteAddr(), question, answer, currentDate);
        assertThat(httpHandler.getRequestBody(),
                is("{\"ip\": \"10.10.10.10\", \"sessionId\": \"sessionId\", \"question\": \"questionContent\", \"answer\": \"answerContent\", \"time\": \"" + FullDateTimeFormator.format(currentDate) + "\"}"));
    }

    private HttpServletRequest createRequest(String clientIp, String sessionId) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRemoteAddr(clientIp);
        mockHttpServletRequest.setRequestedSessionId(sessionId);
        return mockHttpServletRequest;
    }

}
