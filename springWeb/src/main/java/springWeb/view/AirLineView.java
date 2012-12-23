package springWeb.view;

import com.google.common.base.Strings;
import org.codehaus.jackson.annotate.JsonProperty;
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

    @JsonProperty
    public List<Cabin> getCabins() {
        return cabins;
    }

    @JsonProperty
    public String getPrice() {
        return price;
    }

    @JsonProperty
    public String getFlightNumber() {
        return flightNumber;
    }

    @JsonProperty
    public String getArriveDate() {
        return arriveDate;
    }

    @JsonProperty
    public String getDepartureDate() {
        return departureDate;
    }

    @JsonProperty
    public String getOrgAirport() {
        return orgAirport;
    }

    @JsonProperty
    public String getDstAirport() {
        return dstAirport;
    }

    private String formatPrice(String orgPrice) {
        String priceInt = orgPrice.replaceAll("\\.\\d+", "");
        return priceInt + "\u5143";
    }

    String formatDate(String date) {
        return Strings.nullToEmpty(date).replace("T", " ").replaceAll(":[0-9][0-9]$", "");
    }
}
