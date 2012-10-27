package springWeb.airTicket.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class AirLine {
    @JacksonXmlProperty(localName = "Cabins")
    private List<Cabin> cabins;

    @JacksonXmlProperty(localName = "Airline")
    private String airLineNumber;

    @JacksonXmlProperty(localName = "FlightNo")
    private String flightNumber;

    @JacksonXmlProperty(localName = "OrgDateTime")
    private String departureDate;

    @JacksonXmlProperty(localName = "AirportTax")
    private String airportTax;

    @JacksonXmlProperty(localName = "BasePrice")
    private String basePrice;

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirportTax() {
        return airportTax;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public List<Cabin> getCabins() {
        return cabins;
    }

    public String getAirLineNumber() {
        return airLineNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }
}
