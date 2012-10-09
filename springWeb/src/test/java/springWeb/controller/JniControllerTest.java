package springWeb.controller;

import com.google.common.base.Splitter;
import org.junit.Test;

public class JniControllerTest {
    @Test
    public void should_() {
        String s = "fafdaf<ChatStateContainer>abc</ChatStateContainer>";
        int i = s.indexOf("<ChatStateContainer>");
        System.out.println(s.substring(0,i));
        System.out.println(s.substring(i));
    }
}
