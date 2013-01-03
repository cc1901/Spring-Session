package springWeb.view;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class TicketAnswer {
    @JsonProperty("airLines")
    private List<AirLineView> airLines;

    @JsonProperty("userAnswerPrefix")
    private String userAnswerPrefix;

    @JsonProperty("userAnswerSuffix")
    private String userAnswerSuffix;

    @JsonProperty("ticketQuery")
    private TicketQueryView ticketQuery;

    public TicketAnswer(List<AirLineView> airLines, String userAnswerPrefix, String userAnswerSuffix, TicketQueryView ticketQuery) {
        this.airLines = airLines;
        this.userAnswerPrefix = userAnswerPrefix;
        this.userAnswerSuffix = userAnswerSuffix;
        this.ticketQuery = ticketQuery;
    }

    @JsonProperty
    public String getUserAnswerSuffix() {
        return userAnswerSuffix;
    }

    @JsonProperty
    public List<AirLineView> getAirLines() {
        return airLines;
    }

    @JsonProperty
    public String getUserAnswerPrefix() {
        return userAnswerPrefix;
    }
}
