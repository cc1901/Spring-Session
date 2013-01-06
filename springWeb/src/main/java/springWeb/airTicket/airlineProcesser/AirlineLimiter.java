package springWeb.airTicket.airlineProcesser;

import springWeb.airTicket.response.model.AirLine;

import java.util.List;

public class AirlineLimiter {
    public static List<AirLine> limit(List<AirLine> airLines) {
        return airLines.subList(0, Math.min(airLines.size(), 5));
    }
}
