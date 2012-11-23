package springWeb.util;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Utf8LoggerTest {
    @Test
    public void should_print_utf8_string() throws UnsupportedEncodingException {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String testUtf8String = "测试utf8字符";
        new Utf8Logger().printLog(testUtf8String);

        assertThat(myOut.toString(), is(testUtf8String + "\n"));
        System.setOut(System.out);
    }
}
