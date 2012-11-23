package springWeb.airTicket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.junit.Test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TicketQueryParserTest {
    @Test
    public void should_parser_ticket_query() throws IOException {
        TicketQueryParser ticketQueryParser = new TicketQueryParser();
        TicketQuery ticketQuery = ticketQueryParser.parse("{\"criteria\":{\"departure\":\"PEK\",\"destination\":\"Xi'an\",\"departureDate\":\"2012-12-13\"},\"sortBy\":\"mostCheap\",\"filter\":[\"<filter1>\",\"<filter2>\"]}");
        assertThat(ticketQuery.getCriteria(), is(new Criteria("PEK", "Xi'an", "2012-12-13")));
        assertThat(ticketQuery.getCriteria().getDeparture(), is("PEK"));
        assertThat(ticketQuery.getCriteria().getDestination(), is("Xi'an"));
        assertThat(ticketQuery.getCriteria().getDepartureDate(), is("2012-12-13"));
        assertThat(ticketQuery.getSortBy(), is("mostCheap"));
        assertThat(ticketQuery.getFilter().get(0), is("<filter1>"));
        assertThat(ticketQuery.getFilter().get(1), is("<filter2>"));
    }

    @Test
    public void should() throws IOException {
        TicketQueryParser ticketQueryParser = new TicketQueryParser();
        TicketQuery ticketQuery = ticketQueryParser.parse("{\n" +
                "        \"criteria\" : {\n" +
                "                \"departure\" : \"PEK\",\n" +
                "                \"departureDate\" : \"2012-11-20\",\n" +
                "                \"destination\" : \"SHA\"\n" +
                "        },\n" +
                "        \"sortBy\" : \"price\",\n" +
                "        \"filter\" : [\n" +
                "                {\n" +
                "                        \"arriveTime\" : \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                        \"company\" : \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                        \"departureTime\" : \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                        \"discount\" : \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                        \"price\" : \"\"\n" +
                "                }\n" +
                "        ]\n" +
                "}");


    }

    @Test
    public void should_() {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<Request>\n" +
                "<FlightSearchRequest>\n" +
                "  <TravelType>Single</TravelType> \n" +
                "  <Departure>PEK</Departure> \n" +
                "  <TravelSummaryCollection>\n" +
                "       <TravelSummary>summery1</TravelSummary>\n" +
                "       <TravelSummary>summery2</TravelSummary>\n" +
                "  </TravelSummaryCollection>\n" +
                "  <TravelDetail>\n" +
                "       <TravelStart>start</TravelStart>\n" +
                "       <TravelEnd>end</TravelEnd>\n" +
                "  </TravelDetail>\n" +
                "</FlightSearchRequest>\n" +
                "</Request>\n";
        try {
            Request request = xmlMapper.readValue(xml, Request.class);
            FlightSearchRequest flightSearchRequest = request.getFlightSearchRequest();
            assertThat(flightSearchRequest.getDeparture(), is("PEK"));
            assertThat(flightSearchRequest.getTravelType(), is("Single"));
            assertThat(flightSearchRequest.getTravelSummaries().get(0), is("summery1"));
            assertThat(flightSearchRequest.getTravelSummaries().get(1), is("summery2"));
//            assertThat(flightSearchRequest.getTravelDetail().getTravelStart(), is("start"));
//            assertThat(flightSearchRequest.getTravelDetail().getTravelEnd(), is("end"));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@JacksonXmlRootElement(localName = "Request")
class Request {
    @JacksonXmlProperty(localName = "FlightSearchRequest")
    private FlightSearchRequest flightSearchRequest;

    public FlightSearchRequest getFlightSearchRequest() {
        return flightSearchRequest;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
class FlightSearchRequest {
    @JacksonXmlProperty(localName = "TravelType")
    private String travelType;

    @JacksonXmlProperty(localName = "Departure")
    private String departure;

    @JacksonXmlProperty(localName = "TravelSummaryCollection")
    private List<String> travelSummaries;

//    @JacksonXmlProperty(localName = "TravelDetail")
//    private TravelDetail travelDetail;

//    public TravelDetail getTravelDetail() {
//        return travelDetail;
//    }

    public List<String> getTravelSummaries() {
        return travelSummaries;
    }

    public String getTravelType() {
        return travelType;
    }

    public String getDeparture() {
        return departure;
    }
}


@XmlAccessorType(XmlAccessType.FIELD)
class TravelDetail {
    @JacksonXmlProperty(localName = "TravelStart")
    private String travelStart;

    @JacksonXmlProperty(localName = "TravelEnd")
    private String travelEnd;

    public String getTravelStart() {
        return travelStart;
    }

    public String getTravelEnd() {
        return travelEnd;
    }
}
