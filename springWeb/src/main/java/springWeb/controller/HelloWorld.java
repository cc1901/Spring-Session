package springWeb.controller;

public class HelloWorld {
    private native String test();

    public static void main(String[] args) {
        new HelloWorld().test();
    }

    public String jniTest() {
        return test();
    }

    static {
        System.load("/var/lib/tomcat6/webapps/chat-engin/WEB-INF/lib/libhelloworld.so");
        //System.load("/Users/twer/homeideas/Spring-Session/springWeb/src/main/java/libhelloworld.jnilib");
//        System.load("libhelloworld.jnilib");
    }
}
