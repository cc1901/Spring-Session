package springWeb.controller;

import springWeb.airTicket.response.model.AirLine;

import java.util.List;

public class TicketAnswer {
    private List<AirLine> airLines;
    private String userAnswer;

    public TicketAnswer(List<AirLine> airLines, String userAnswer) {
        this.airLines = airLines;
        this.userAnswer = userAnswer;
    }

    public List<AirLine> getAirLines() {
        return airLines;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
