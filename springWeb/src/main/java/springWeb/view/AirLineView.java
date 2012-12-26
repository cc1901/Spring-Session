package springWeb.view;

import com.google.common.base.Strings;
import org.codehaus.jackson.annotate.JsonProperty;
import springWeb.airTicket.response.model.Cabin;

import java.util.List;

import static springWeb.helper.AirportMapping.getAirportName;

public class AirLineView {
    private final List<Cabin> cabins;
    private final String price;
    private final String flightNumber;
    private final String arriveDate;
    private final String departureDate;
    private final String orgAirport;
    private final String dstAirport;
    public static final String AIRPORT_URL_TEMPLATE = "http://jipiao.9588.com/home/search?MoreTrip%5B0%5D.fromcity=%s&MoreTrip%5B0%5D.from=%s&MoreTrip%5B0%5D.tocity=%s&MoreTrip%5B0%5D.to=%s&MoreTrip%5B0%5D.date=%s&MoreTrip%5B1%5D.date=%s&segnum=0&quantity=1&airline=&classtype=&traveltype=Single";

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
    public String getAirportUrl() {
        String airportUrl = "http://jipiao.9588.com/home/search?MoreTrip%5B0%5D.fromcity=" +
                getAirportName(orgAirport) +
                "&MoreTrip%5B0%5D.from=" +
                orgAirport +
                "&MoreTrip%5B0%5D.tocity=" +
                getAirportName(dstAirport) +
                "&MoreTrip%5B0%5D.to=" +
                dstAirport +
                "&MoreTrip%5B0%5D.date=" +
                formatDateForUrl(departureDate) +
                "&MoreTrip%5B1%5D.date=" +
                formatDateForUrl(arriveDate) +
                "&segnum=0&quantity=1&airline=&classtype=&traveltype=Single";
        return airportUrl;
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

    private String formatDate(String date) {
        return Strings.nullToEmpty(date).replace("T", " ").replaceAll(":[0-9][0-9]$", "");
    }

    private String formatDateForUrl(String date) {
        return date.replaceAll(" .*", "");
    }

}
