package springWeb.service;

import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

class HttpRequestHandler extends AbstractHandler {
    private String requestJson = "";

    @Override
    public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i) throws IOException, ServletException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);
        requestJson = jb.toString();
    }

    public String getRequestBody() {
        return requestJson;
    }
}
