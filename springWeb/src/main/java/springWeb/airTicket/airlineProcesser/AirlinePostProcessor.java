package springWeb.airTicket.airlineProcesser;

import springWeb.airTicket.TicketQuery;
import springWeb.airTicket.response.model.AirLine;

import java.util.List;

public class AirlinePostProcessor {
    public static List<AirLine> postProcess(TicketQuery ticketQuery, List<AirLine> queriedAirlines) {
        List<AirLine> airLines = AirlineLimiter.limit(queriedAirlines);
        AirlinePriceCalculator.calculate(airLines);
        AirlineSorter.sort(airLines, ticketQuery);
        return airLines;
    }
}
