package springWeb.airTicket.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@JacksonXmlRootElement(localName = "FlightSearchReply")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketQueryResponse {
    @JacksonXmlProperty(localName = "LinesCollection")
    private LinesCollection linesCollection;

    public LinesCollection getLinesCollection() {
        return linesCollection;
    }
}

