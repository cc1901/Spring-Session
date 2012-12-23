package springWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springWeb.beans.StateBean;
import springWeb.service.HomeService;
import springWeb.util.ChatAnswer;
import springWeb.util.Utf8Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
public class HomeController {
//    @Inject
//    @Named
//    private StateBean requestBean;

//    @Inject
//    private StateBean sessionBean;

//    @Autowired
//    private HomeService homeService;

    @RequestMapping("/test1")
    public void index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(true);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + sessionBean.getState());
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + requestBean.getState());
//        requestBean.setState("new state");
//        sessionBean.setState("new state");
    }

    @RequestMapping(value = "/test-json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ChatAnswer testJson(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String a = "24";
        String str = "\u5143";
        new Utf8Logger().printLog("\u4e16");
        new Utf8Logger().printLog("\u4e16\u754c");
        new Utf8Logger().printLog("\u4e16\u754c\u4f60");
        new Utf8Logger().printLog("\u4e16\u754c\u4f60\u597d");
        new Utf8Logger().printLog("\u4e16\u754c\u4f60\u597d\uff01");
        new Utf8Logger().printLog(a + str);
        String encode = "\u4e16";
        System.out.println(a + encode);
        ChatAnswer chatAnswer = new ChatAnswer();
        chatAnswer.setAnswer(a + str);
        return chatAnswer;
    }
}
