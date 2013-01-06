package springWeb.service;

import com.google.common.collect.Lists;
import springWeb.airTicket.*;
import springWeb.airTicket.airlineProcesser.AirlinePostProcessor;
import springWeb.airTicket.response.model.AirLine;
import springWeb.airTicket.response.model.TicketQueryResponse;
import springWeb.view.TicketAnswer;
import springWeb.helper.AirLineViewsHelper;
import springWeb.util.Utf8Logger;
import springWeb.view.TicketQueryView;

import java.io.IOException;
import java.util.List;

public class TicketUserAnswerResolver {
    private static final String TICKET_QUERY_FLAG = "<$query$>";
    public static final String BREAK = "<$break$>";
    private Utf8Logger utf8Logger = new Utf8Logger();

    public TicketUserAnswerResolver() {
    }

    public TicketAnswer getTicketAnswer(String answer) throws IOException {
        List<AirLine> airLines = Lists.newArrayList();
        TicketQueryView ticketQueryView = new TicketQueryView();
        if (hasQuery(answer)) {
            TicketQueryResponse ticketQueryResponse = new TicketQueryResponse();
            utf8Logger.printLog("fetch+++++++++++++++++++++");
            String query = getQuery(answer);
            utf8Logger.printLog("query: " + query);
            utf8Logger.printLog("+++++++++++++++++++++++++++");
            TicketQueryParser ticketQueryParser = new TicketQueryParser();
            TicketQuery ticketQuery = ticketQueryParser.parse(query);
            TicketInformationFetcher ticketInformationFetcher = new TicketInformationFetcher();
            ticketQueryResponse = ticketInformationFetcher.fetch(ticketQuery.getCriteria());
            ticketQueryView = new TicketQueryView(ticketQuery.getCriteria());
            if (noAirTicketInfo(ticketQueryResponse)) {
                String userAnswer = new String("no ticket info");
                return new TicketAnswer(AirLineViewsHelper.transferAirLineView(airLines), userAnswer, "", ticketQueryView);
            }

            airLines = AirlinePostProcessor.postProcess(ticketQuery, ticketQueryResponse.getLinesCollection().getLines().getAirLines());
        }
        return getTicketAnswer(answer, airLines, ticketQueryView);
    }

    private String getQuery(String answer) {
        int queryStart = answer.indexOf(TICKET_QUERY_FLAG) + TICKET_QUERY_FLAG.length();
        int queryEnd = answer.indexOf(BREAK);
        return answer.substring(queryStart, queryEnd);
    }

    private TicketAnswer getTicketAnswer(String answer, List<AirLine> airLines, TicketQueryView ticketQueryView) {
        String userAnswer = answer;
        String userAnswerPrefix = "";
        String userAnswerSuffix = "";
        if (hasQuery(answer)) {
            userAnswerSuffix = userAnswer.substring(userAnswer.indexOf(BREAK) + (BREAK).length());
            userAnswerPrefix = userAnswer.substring(0, userAnswer.indexOf(TICKET_QUERY_FLAG));
            return new TicketAnswer(AirLineViewsHelper.transferAirLineView(airLines), userAnswerPrefix, userAnswerSuffix, ticketQueryView);
        }
        return new TicketAnswer(AirLineViewsHelper.transferAirLineView(airLines), userAnswer, userAnswerSuffix, ticketQueryView);
    }

    private boolean noAirTicketInfo(TicketQueryResponse ticketQueryResponse) {
        return ticketQueryResponse.getLinesCollection() == null ||
                ticketQueryResponse.getLinesCollection().getLines() == null ||
                ticketQueryResponse.getLinesCollection().getLines().getAirLines() == null;
    }

    private boolean hasQuery(String answer) {
        return answer.contains(TICKET_QUERY_FLAG);
    }
}
