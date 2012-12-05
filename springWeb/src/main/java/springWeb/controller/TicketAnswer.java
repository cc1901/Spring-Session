package springWeb.controller;

import springWeb.airTicket.response.model.AirLine;

import java.util.List;

public class TicketAnswer {
    private List<AirLine> airLines;
    private String userAnswerPrefix;
    private String userAnswerSuffix;

    public TicketAnswer(List<AirLine> airLines, String userAnswerPrefix, String userAnswerSuffix) {
        this.airLines = airLines;
        this.userAnswerPrefix = userAnswerPrefix;
        this.userAnswerSuffix = userAnswerSuffix;
    }

    public String getUserAnswerSuffix() {
        return userAnswerSuffix;
    }

    public List<AirLine> getAirLines() {
        return airLines;
    }

    public String getUserAnswerPrefix() {
        return userAnswerPrefix;
    }
}
