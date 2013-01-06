package springWeb.airTicket.airlineProcesser;

import springWeb.airTicket.response.model.AirLine;

import java.util.List;

public class AirlinePriceCalculator {
    public static void calculate(List<AirLine> airLines) {
        for (AirLine airLine : airLines) {
            airLine.calculatePrice();
        }
    }
}
