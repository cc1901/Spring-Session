package springWeb.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChatServerHandler extends HttpRequestHandler {

    private String answer;
    private String context;

    public ChatServerHandler(String answer, String context) {
        this.answer = answer;
        this.context = context;
    }

    @Override
    public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        String chatResponseJson = "{\"answer\":\"" + answer + "\", \"context\":\"" + context + "\"}";
        response.getWriter().write(chatResponseJson);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
