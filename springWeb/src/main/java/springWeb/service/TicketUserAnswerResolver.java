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
            if (ticketQueryResponse.getLinesCollection() == null) {
                this.contextResolver.setContext(answer);
                String userAnswer = new String("no ticket info");
                return new TicketAnswer(airLines, userAnswer);
            }
            airLines = ticketQueryResponse.getLinesCollection().getLines().getAirLines();

            for (AirLine airLine : airLines) {
                airLine.calculatePrice();
            }
            TicketSorter.sort(airLines, ticketQuery);
            airLines = airLines.subList(0, Math.min(airLines.size() - 1, 5));

        }
        int indexOfContext = this.contextResolver.setContext(answer);
        String userAnswer = getUserAnswer(answer, indexOfContext);
        return new TicketAnswer(airLines, userAnswer);
    }

    private String getUserAnswer(String answer, int indexOfContext) {
        String userAnswer = answer;
        if (indexOfContext >= 0) {
            userAnswer = answer.substring(0, indexOfContext);
        }
        if (hasQuery(answer)) {
            userAnswer = answer.substring(answer.indexOf(BREAK) + (BREAK).length(), indexOfContext);
        }
        return userAnswer;
    }

    private boolean hasQuery(String answer) {
        return answer.contains(TICKET_QUERY_FLAG) && answer.indexOf(CHAT_CONTEXT_CONTAINER) > answer.indexOf(TICKET_QUERY_FLAG);
    }

}
