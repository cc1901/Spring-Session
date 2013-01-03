package springWeb.view;

import org.codehaus.jackson.annotate.JsonProperty;
import springWeb.airTicket.Criteria;

public class TicketQueryView {
    @JsonProperty("departureDate")
    private String departureDate;

    @JsonProperty("departure")
    private String departure;

    @JsonProperty("destination")
    private String destination;

    public TicketQueryView(Criteria criteria) {
        this.departureDate = criteria.getDepartureDate();
        this.departure = criteria.getDeparture();
        this.destination = criteria.getDestination();
    }

    public TicketQueryView() {
        this.departureDate = "";
        this.departure = "";
        this.destination = "";
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }
}
