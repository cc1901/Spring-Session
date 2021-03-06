package springWeb.airTicket.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Cabin {
    @JacksonXmlProperty(localName = "CabinType")
    private String cabinType;

    @JacksonXmlProperty(localName = "ChildiePrice")
    private String childiePrice;

    @JacksonXmlProperty(localName = "AdultPrice")
    private String adultPrice;

    public Cabin() {
    }

    public Cabin(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getCabinType() {
        return cabinType;
    }

    public String getChildiePrice() {
        return childiePrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }
}
