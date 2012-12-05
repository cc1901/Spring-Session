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

public class ChatEngineRemoteTest {
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
    public void should_send_chat_info_to_chat_server() {

    }
}
