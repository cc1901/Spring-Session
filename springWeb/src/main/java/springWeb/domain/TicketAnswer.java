package springWeb.domain;

import springWeb.airTicket.response.model.AirLine;
import springWeb.helper.AirLineViewsHelper;
import springWeb.view.AirLineView;

import java.util.List;

public class TicketAnswer {
    private List<AirLineView> airLines;
    private String userAnswerPrefix;
    private String userAnswerSuffix;

    public TicketAnswer(List<AirLineView> airLines, String userAnswerPrefix, String userAnswerSuffix) {
        this.airLines = airLines;
        this.userAnswerPrefix = userAnswerPrefix;
        this.userAnswerSuffix = userAnswerSuffix;
    }

    public String getUserAnswerSuffix() {
        return userAnswerSuffix;
    }

    public List<AirLineView> getAirLines() {
        return airLines;
    }

    public String getUserAnswerPrefix() {
        return userAnswerPrefix;
    }
}
