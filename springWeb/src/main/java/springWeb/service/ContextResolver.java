package springWeb.service;

import javax.servlet.http.HttpSession;

public class ContextResolver {
    private static final String CONTEXT_KEY = "context";
    private static final int CONTEXT_EXPIRE_TIME = 600;
    HttpSession session;

    public ContextResolver(HttpSession session) {
        this.session = session;
        session.setMaxInactiveInterval(CONTEXT_EXPIRE_TIME);
    }

    public void setContextInCookie(String newContext) {
        this.session.setAttribute(CONTEXT_KEY, newContext);
    }

    public String getContext() {
        return (String) session.getAttribute(CONTEXT_KEY);
    }
}
