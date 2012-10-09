package springWeb.controller;

public class HelloWorld {
    private native String test(String text, String context);

    public static void main(String[] args) {
        new HelloWorld().test("", "");
    }

    public String jniTest(String text, String context) {
        return test(text, context);
    }

    static {
        try {
            System.load("/var/lib/tomcat6/webapps/chat-engine/WEB-INF/lib/libhelloworld.so");
        } catch (Exception e) {
            System.out.println("test");
            System.out.println(e);
        }
        //System.load("/Users/twer/homeideas/Spring-Session/springWeb/src/main/java/libhelloworld.jnilib");
//        System.load("libhelloworld.jnilib");
    }
}
