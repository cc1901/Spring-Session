package springWeb.service;

import com.google.common.collect.Lists;
import springWeb.airTicket.TicketInformationFetcher;
import springWeb.airTicket.TicketQuery;
import springWeb.airTicket.TicketQueryParser;
import springWeb.airTicket.response.model.AirLine;
import springWeb.airTicket.response.model.TicketQueryResponse;
import springWeb.controller.TicketAnswer;
import springWeb.util.Utf8Logger;

import java.io.IOException;
import java.util.List;

public class TicketUserAnswerResolver {
    private static final String TICKET_QUERY_FLAG = "<$query$>";
    public static final String BREAK = "<$break$>";
    public static final String CHAT_CONTEXT_CONTAINER = "<ChatStateContainer>";
    private Utf8Logger utf8Logger = new Utf8Logger();
    private ContextResolver contextResolver;

    public TicketUserAnswerResolver(ContextResolver contextResolver) {
        this.contextResolver = contextResolver;
    }

    public TicketAnswer getTicketAnswer(String answer) throws IOException {
        List<AirLine> airLines = Lists.newArrayList();
        if (hasQuery(answer)) {
            TicketQueryResponse ticketQueryResponse = new TicketQueryResponse();
            utf8Logger.printLog("fetch+++++++++++++++++++++");
            int queryStart = answer.indexOf(TICKET_QUERY_FLAG) + TICKET_QUERY_FLAG.length();
            int queryEnd = answer.indexOf(BREAK);
            String query = answer.substring(queryStart, queryEnd);
            utf8Logger.printLog(query);
            utf8Logger.printLog("+++++++++++++++++++++++++++");
            TicketQueryParser ticketQueryParser = new TicketQueryParser();
            TicketQuery ticketQuery = ticketQueryParser.parse(query);
            TicketInformationFetcher ticketInformationFetcher = new TicketInformationFetcher();
            ticketQueryResponse = ticketInformationFetcher.fetch(ticketQuery.getCriteria());
            if (noAirTicketInfo(ticketQueryResponse)) {
                String userAnswer = new String("no ticket info");
                this.contextResolver.setContext(answer);
                return new TicketAnswer(airLines, userAnswer, "");
            }
            List<AirLine> queriedAirLines = ticketQueryResponse.getLinesCollection().getLines().getAirLines();
            airLines = airTicketsAfterProcess(airLines, ticketQuery, queriedAirLines);
        }
        int indexOfContext = this.contextResolver.setContext(answer);
        return getTicketAnswer(answer, airLines, indexOfContext);
    }

    private TicketAnswer getTicketAnswer(String answer, List<AirLine> airLines, int indexOfContext) {
        String userAnswer = answer;
        String userAnswerPrefix = "";
        String userAnswerSuffix = "";
        if (indexOfContext >= 0) {
            userAnswer = answer.substring(0, indexOfContext);
        }
        if (hasQuery(answer)) {
            userAnswerSuffix = userAnswer.substring(userAnswer.indexOf(BREAK) + (BREAK).length());
            userAnswerPrefix = userAnswer.substring(0, userAnswer.indexOf(TICKET_QUERY_FLAG));
            return new TicketAnswer(airLines, userAnswerPrefix, userAnswerSuffix);
        }
        return new TicketAnswer(airLines, userAnswer, userAnswerSuffix);
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
        return answer.contains(TICKET_QUERY_FLAG) && answer.indexOf(CHAT_CONTEXT_CONTAINER) > answer.indexOf(TICKET_QUERY_FLAG);
    }

}
