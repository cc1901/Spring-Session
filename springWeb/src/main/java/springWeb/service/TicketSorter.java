package springWeb.service;

import com.google.common.collect.Ordering;
import springWeb.airTicket.TicketQuery;
import springWeb.airTicket.response.model.AirLine;

import java.util.Collections;
import java.util.List;

class TicketSorter {
    public static void sort(List<AirLine> airLines, TicketQuery ticketQuery) {
        if (ticketQuery.getSortBy().contains("price")) {
            Collections.sort(airLines, new Ordering<AirLine>() {
                @Override
                public int compare(AirLine airLine, AirLine airLine1) {
                    return Double.compare(Double.parseDouble(airLine.getPrice()), Double.parseDouble(airLine1.getPrice()));
                }
            });
        }
    }
}
