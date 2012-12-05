package springWeb.service;

import javax.servlet.http.HttpSession;

public class ContextResolver {
    private static final String CONTEXT = "context";
    public static final int CONTEXT_EXPIRE_TIME = 600;
    public static final String CONTEXT_CONTAINER = "<ChatStateContainer>";
    HttpSession session;

    public ContextResolver(HttpSession session) {
        this.session = session;
        session.setMaxInactiveInterval(CONTEXT_EXPIRE_TIME);
    }

    int setContext(String answer) {
        int indexOfContext = answer.indexOf(CONTEXT_CONTAINER);
        String newContext = answer;
        if (indexOfContext >= 0) {
            newContext = answer.substring(indexOfContext);
        }
        this.session.setAttribute(CONTEXT, newContext);
        return indexOfContext;
    }

    public String getContext() {
        return (String) session.getAttribute(CONTEXT);
    }
}
