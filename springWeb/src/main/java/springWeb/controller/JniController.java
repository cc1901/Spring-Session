package springWeb.controller;

import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JniController {
    @Autowired
    private HelloWorld helloWorld;

    @RequestMapping("/home")
    public String talkPage() {
        return "test";
    }

    @RequestMapping("/talk")
    @ResponseBody
    public String talk(
            @RequestParam(value = "input", required = true) String input
    ) {
        System.out.println(input);
        System.out.println(helloWorld.jniTest());
        return helloWorld.jniTest();
    }
}
