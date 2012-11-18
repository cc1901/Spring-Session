package springWeb.service;

public class ChatEngine {
    private native String chat(String text, String context);

    public static void main(String[] args) {
        new ChatEngine().chat("", "");
    }

    public String chatWithEngine(String text, String context) {
        return chat(text, context);
    }

    static {
//        try {
//            System.load("/var/lib/tomcat6/webapps/chat-engine/WEB-INF/lib/libhelloworld.so");
//        } catch (Exception e) {
//            System.out.println("test");
//            System.out.println(e);
//        }
        System.load("/Users/twer/homeideas/Spring-Session/springWeb/src/main/java/libChatEngine.jnilib");
    }
}
