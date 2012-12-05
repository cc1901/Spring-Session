package springWeb.service;

public class ChatEngine implements ChatEngineInterface {
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
}
