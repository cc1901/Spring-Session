package springWeb.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Utf8Logger {
    private PrintStream utf8Printer = System.out;

    public Utf8Logger() {
        try {
            utf8Printer = new PrintStream(System.out, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void printLog(String message) {
        utf8Printer.println(message);
    }
}
