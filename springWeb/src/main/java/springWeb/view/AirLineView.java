package springWeb.view;

import com.google.common.base.Strings;
import springWeb.airTicket.response.model.Cabin;

import java.util.List;

public class AirLineView {
    private final List<Cabin> cabins;
    private final String price;
    private final String flightNumber;
    private final String arriveDate;
    private final String departureDate;
    private final String orgAirport;
    private final String dstAirport;

    public AirLineView(List<Cabin> cabins, String price, String flightNumber, String departureDate, String arriveDate, String orgAirport, String dstAirport) {
        this.cabins = cabins;
        this.price = formatPrice(price);
        this.flightNumber = flightNumber;
        this.arriveDate = formatDate(arriveDate);
        this.departureDate = formatDate(departureDate);
        this.orgAirport = orgAirport;
        this.dstAirport = dstAirport;
    }

    public List<Cabin> getCabins() {
        return cabins;
    }

    public String getPrice() {
        return price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getOrgAirport() {
        return orgAirport;
    }

    public String getDstAirport() {
        return dstAirport;
    }

    private String formatPrice(String price) {
        String priceInt = price.replaceAll("\\.\\d+", "");
        return priceInt + "元";
    }

    String formatDate(String date) {
        return Strings.nullToEmpty(date).replace("T", " ").replaceAll(":[0-9][0-9]$", "");
    }
}
