package springWeb.service;

import springWeb.domain.ChatResponse;

public class ChatEngine implements ChatEngineInterface {
    public static final String CONTEXT_CONTAINER = "<ChatStateContainer>";
    public static final String CONTEXT_CONTAINER_END = "</ChatStateContainer>";
    private static String configFile;

    public ChatEngine(String configFile) {
        ChatEngine.configFile = configFile;
        loadJniLib();
    }

    private void loadJniLib() {
        try {
            System.load("/var/lib/tomcat6/webapps/chat-engine/WEB-INF/lib/libChatEngine1.so");
        } catch (Exception e) {
            System.out.println(e);
        }
//        System.load("/Users/twer/homeideas/Spring-Session/springWeb/src/main/java/libChatEngine.jnilib");
    }

    public static String configFile() {
        return ChatEngine.configFile;
    }

    private native String chat(String text, String context);

    public static void main(String[] args) {
        new ChatEngine("test...").chat("", "");
    }

    public String chatWithEngine(String text, String context) {
        return chat(text, context);
    }

    @Override
    public ChatResponse chatWithEngine1(String input, String context) {
        String chatEngineAnswer = chat(input, context);
        int indexOfContext = chatEngineAnswer.indexOf(CONTEXT_CONTAINER);
        int indexOfContextEnd = chatEngineAnswer.indexOf(CONTEXT_CONTAINER_END);
        int startOfNewContext = indexOfContext + CONTEXT_CONTAINER.length();
        String newContext = chatEngineAnswer.substring(startOfNewContext, indexOfContextEnd);
        String answer = chatEngineAnswer.substring(0, indexOfContext);
        return new ChatResponse(answer, newContext);
    }
}
