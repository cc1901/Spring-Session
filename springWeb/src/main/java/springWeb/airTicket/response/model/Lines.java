package springWeb.airTicket.response.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Lines {
    @JacksonXmlProperty(localName = "AirItems")
    private List<AirLine> airLines;

    public List<AirLine> getAirLines() {
        return airLines;
    }
}

