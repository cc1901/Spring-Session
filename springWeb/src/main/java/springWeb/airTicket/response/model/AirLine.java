package springWeb.airTicket.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @JacksonXmlProperty(localName = "DstDateTime")
    private String arriveDate;

    @JacksonXmlProperty(localName = "OrgDateTime")
    private String departureDate;

    @JacksonXmlProperty(localName = "BasePrice")
    private String basePrice;

    @JacksonXmlProperty(localName = "AirportTax")
    private String airportTax;

    @JacksonXmlProperty(localName = "OrgAirport")
    private String orgAirport;

    @JacksonXmlProperty(localName = "DstAirport")
    private String dstAirport;

    private String price;

    public String getPrice() {
        return price;
    }

    public String getOrgAirport() {
        return orgAirport;
    }

    public String getDstAirport() {
        return dstAirport;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirportTax() {
        return airportTax;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void calculatePrice() {
        if (cabins == null || cabins.isEmpty()) {
            this.price = basePrice;
        } else {
            this.price = Collections.min(cabins, new Comparator<Cabin>() {
                @Override
                public int compare(Cabin cabin, Cabin cabin1) {
                    return Double.compare(Double.parseDouble(cabin.getAdultPrice()), Double.parseDouble(cabin1.getAdultPrice()));
                }
            }).getAdultPrice();
        }
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

    public String getArriveDate() {
        return arriveDate;
    }

    public void setCabins(ArrayList<Cabin> cabins) {
        this.cabins = cabins;
    }
}
