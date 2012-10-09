package springWeb.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class JniController {
    public static final String CONTEXT = "context";
    @Autowired
    private HelloWorld helloWorld;

    @RequestMapping("/home")
    public String talkPage() {
        return "test";
    }

    @RequestMapping("/talk")
    @ResponseBody
    public String talk(
            @RequestParam(value = "input", required = true) String input,
            HttpServletRequest request
    ) {
        System.out.println(input);

        HttpSession session = request.getSession(true);
        String context = (String) session.getAttribute(CONTEXT);

        String answer = helloWorld.jniTest(input, Strings.nullToEmpty(context));
        int indexOfContext = answer.indexOf("<ChatStateContainer>");
        String newContext = answer.substring(indexOfContext);
        session.setAttribute(CONTEXT, newContext);
        return answer.substring(0, indexOfContext);
    }
}
