package springWeb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springWeb.airTicket.TicketInformationFetcher;
import springWeb.domain.ChatResponse;
import springWeb.service.*;
import springWeb.util.Utf8Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class ChatController {
    private Utf8Logger utf8Logger = new Utf8Logger();
    @Autowired
    private ChatService chatService;

    @Autowired
    private WelcomeSentence welcomeSentence;

    @Autowired
    private ChatInfoLogger chatInfoLogger;

    @Autowired
    private TicketInformationFetcher ticketInformationFetcher;

    private Logger logger = LoggerFactory.getLogger("jni");

    @RequestMapping("/home")
    public String talkPage(ModelMap modelMap) {
        modelMap.put("welcomeSentence", welcomeSentence.getWelcomeSentence());
        modelMap.put("chatUrlJs", "chat-url");
        return "chatPage";
    }

    @RequestMapping(value = "/talk", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TicketAnswer talk(
            @RequestParam(value = "input", required = true) String input,
            HttpServletRequest request
    ) throws IOException {
        HttpSession session = request.getSession(true);
        ContextResolver contextResolver = new ContextResolver(session);
        String context = contextResolver.getContext();
        utf8Logger.printLog("input: +++++++++++++++" + input);
        utf8Logger.printLog("context: +++++++++++++++" + context);
        ChatResponse chatResponse = chatService.chat1(input, context);
        utf8Logger.printLog("answer:  " + chatResponse.getAnswer() + "++++++++++++++++++++++++++++");
//        String userAnswer = new String(answer.getBytes(), "utf-8");
        chatInfoLogger.logChatHistoryInfo(session.getId(), request.getRemoteAddr(), input, chatResponse.getAnswer(), new Date());
        contextResolver.setContextInCookie(chatResponse.getContext());
        utf8Logger.printLog("answer:============" + chatResponse.getAnswer());
        utf8Logger.printLog("context:===========" + chatResponse.getContext());
        return new TicketUserAnswerResolver().getTicketAnswer(chatResponse.getAnswer());
    }

    @RequestMapping(value = "/clear-session")
    public void talk(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        System.out.println("*****************************");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
