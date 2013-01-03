package springWeb.service;

import com.google.common.collect.Lists;
import springWeb.airTicket.TicketInformationFetcher;
import springWeb.airTicket.TicketQuery;
import springWeb.airTicket.TicketQueryParser;
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
            List<AirLine> queriedAirLines = ticketQueryResponse.getLinesCollection().getLines().getAirLines();
            airLines = airTicketsAfterProcess(airLines, ticketQuery, queriedAirLines);
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

    private List<AirLine> airTicketsAfterProcess(List<AirLine> airLines, TicketQuery ticketQuery, List<AirLine> queriedAirLines) {
        airLines = queriedAirLines;

        for (AirLine airLine : airLines) {
            airLine.calculatePrice();
        }
        TicketSorter.sort(airLines, ticketQuery);
        airLines = airLines.subList(0, Math.min(airLines.size() - 1, 5));
        return airLines;
    }

    private boolean hasQuery(String answer) {
        return answer.contains(TICKET_QUERY_FLAG);
    }
}
