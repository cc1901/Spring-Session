package springWeb.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springWeb.airTicket.TicketInformationFetcher;
import springWeb.airTicket.response.model.AirLine;
import springWeb.airTicket.response.model.TicketQueryResponse;
import springWeb.service.ChatEngine;
import springWeb.service.ChatInfoLogger;
import springWeb.service.ChatService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {
    public static final String CONTEXT = "context";
    public static final String TICKET_QUERY_FLAG = "<$query$>";
    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatInfoLogger chatInfoLogger;

    @Autowired
    private TicketInformationFetcher ticketInformationFetcher;

    private Logger logger = LoggerFactory.getLogger("jni");

    @RequestMapping("/home")
    public String talkPage() {
        return "test";
    }

    @RequestMapping(value = "/talk", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TicketAnswer talk(
            @RequestParam(value = "input", required = true) String input,
            HttpServletRequest request
    ) throws IOException {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(1200);
        String context = (String) session.getAttribute(CONTEXT);
        TicketQueryResponse ticketQueryResponse = new TicketQueryResponse();
        List<AirLine> airLines = Lists.newArrayList();

        PrintStream utf8Out = new PrintStream(System.out, true, "UTF-8");
        utf8Out.println("input: +++++++++++++++" + input);
        utf8Out.println("context: +++++++++++++++" + context);
        String answer = chatService.chat(input, context);
//        utf8Out.println("answer:  " + answer + "++++++++++++++++++++++++++++");
//        if (answer.contains(TICKET_QUERY_FLAG) && answer.indexOf("<ChatStateContainer>") > answer.indexOf(TICKET_QUERY_FLAG)) {
//            utf8Out.println("fetch+++++++++++++++++++++");
//            int queryStart = answer.indexOf(TICKET_QUERY_FLAG) + TICKET_QUERY_FLAG.length();
//            int queryEnd = answer.indexOf("<$break$>");
//            String query = answer.substring(queryStart, queryEnd);
//            TicketQueryParser ticketQueryParser = new TicketQueryParser();
//            TicketQuery ticketQuery = ticketQueryParser.parse(query);
//            TicketInformationFetcher ticketInformationFetcher = new TicketInformationFetcher();
//            ticketQueryResponse = ticketInformationFetcher.fetch(ticketQuery.getCriteria());
//            if (ticketQueryResponse.getLinesCollection() == null) {
//                setContext(session, answer);
//                String userAnswer = new String("no ticket info");
//                return new TicketAnswer(airLines, userAnswer);
//            }
//            airLines = ticketQueryResponse.getLinesCollection().getLines().getAirLines();
//
//            for (AirLine airLine : airLines) {
//                airLine.calculatePrice();
//            }
//            if (ticketQuery.getSortBy().contains("price")) {
//                Collections.sort(airLines, new Ordering<AirLine>() {
//                    @Override
//                    public int compare(AirLine airLine, AirLine airLine1) {
//                        return Double.compare(Double.parseDouble(airLine.getPrice()), Double.parseDouble(airLine1.getPrice()));
//                    }
//                });
//            }
//            airLines = airLines.subList(0, Math.min(airLines.size() - 1, 5));
//
//        }
//        int indexOfContext = setContext(session, answer);
//        String userAnswer = getUserAnswer(answer, indexOfContext);
        String userAnswer = new String(answer.getBytes(), "utf-8");
        chatInfoLogger.logChatHistoryInfo(session.getId(), request.getRemoteAddr(), input, answer, new Date());
        return new TicketAnswer(airLines, userAnswer);
    }

    private String getUserAnswer(String answer, int indexOfContext) {
        String userAnswer = answer.substring(0, indexOfContext);
        if (answer.indexOf(TICKET_QUERY_FLAG) >= 0) {
            userAnswer = answer.substring(answer.indexOf("<$break$>") + ("<$break$>").length(), indexOfContext);
        }
        return userAnswer;
    }

    private int setContext(HttpSession session, String answer) {
        int indexOfContext = answer.indexOf("<ChatStateContainer>");
        String newContext = answer.substring(indexOfContext);
        session.setAttribute(CONTEXT, newContext);
        return indexOfContext;
    }

    @RequestMapping(value = "/clear-session")
    public void talk(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        System.out.println("*****************************");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
