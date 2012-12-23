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

    public TicketAnswer(List<AirLineView> airLines, String userAnswerPrefix, String userAnswerSuffix) {
        this.airLines = airLines;
        this.userAnswerPrefix = userAnswerPrefix;
        this.userAnswerSuffix = userAnswerSuffix;
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
