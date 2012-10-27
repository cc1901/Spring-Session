package springWeb.controller;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springWeb.airTicket.Criteria;
import springWeb.airTicket.TicketInformationFetcher;
import springWeb.airTicket.TicketQuery;
import springWeb.airTicket.TicketQueryParser;
import springWeb.airTicket.response.model.AirLine;
import springWeb.airTicket.response.model.TicketQueryResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ChatController {
    public static final String CONTEXT = "context";
    public static final String TICKET_QUERY_FLAG = "<$query$>";
    @Autowired
    private HelloWorld helloWorld;

    @Autowired
    private TicketInformationFetcher ticketInformationFetcher;

    private Logger logger = LoggerFactory.getLogger("jni");

    @RequestMapping("/home")
    public String talkPage() {
        return "test";
    }

    @RequestMapping("/test1")
    public String test(
            @RequestParam(value = "a", required = true) String a,
            @RequestParam(value = "b", required = true) String b,
            @RequestParam(value = "c", required = true) String c
    ) {
        ticketInformationFetcher.fetchTest(new Criteria(a, b, c));
        return "test1";
    }

    @RequestMapping(value = "/talk", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String talk(
            @RequestParam(value = "input", required = true) String input,
            HttpServletRequest request
    ) throws IOException {
        HttpSession session = request.getSession(true);
        String context = (String) session.getAttribute(CONTEXT);

        logger.info("input: +++++++++++++++" + input);
        String answer = helloWorld.jniTest(input, "-s " + Strings.nullToEmpty(context));
        TicketQueryResponse ticketQueryResponse = null;
        if (answer.contains(TICKET_QUERY_FLAG)) {
            int queryStart = answer.indexOf(TICKET_QUERY_FLAG) + TICKET_QUERY_FLAG.length();
            int queryEnd = answer.indexOf("<$break$>");
            String query = answer.substring(queryStart, queryEnd - queryStart);
            TicketQueryParser ticketQueryParser = new TicketQueryParser();
            TicketQuery ticketQuery = ticketQueryParser.parse(query);
            TicketInformationFetcher ticketInformationFetcher = new TicketInformationFetcher();
            ticketQueryResponse = ticketInformationFetcher.fetch(ticketQuery.getCriteria());
        }
        List<AirLine> airLines = ticketQueryResponse.getLinesCollection().getLines().getAirLines();

        int indexOfContext = answer.indexOf("<ChatStateContainer>");
        String newContext = answer.substring(indexOfContext);
        String userAnswer = answer.substring(0, indexOfContext);
        session.setAttribute(CONTEXT, newContext);
//        String answer = new String("你妹的".getBytes(),"utf-8");
        logger.info("answer: +++++++++++++++" + answer);
        return answer;
    }
}
