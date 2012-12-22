package springWeb.helper;

import com.google.common.collect.Lists;
import springWeb.airTicket.response.model.AirLine;
import springWeb.view.AirLineView;

import java.util.List;

public class AirLineViewsHelper {
    public static List<AirLineView> transferAirLineView(List<AirLine> airLines) {
        List<AirLineView> airLineViews = Lists.newArrayList();
        for (AirLine airLine : airLines) {
            AirLineView airLineView = new AirLineView(airLine.getCabins(),
                    airLine.getPrice(),
                    airLine.getFlightNumber(),
                    airLine.getDepartureDate(), airLine.getArriveDate(),
                    airLine.getOrgAirport(),
                    airLine.getDstAirport());
            airLineViews.add(airLineView);
        }
        return airLineViews;
    }
}
