package springWeb.airTicket;

import org.codehaus.jackson.annotate.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Criteria {
    public Criteria() {

    }

    @JsonProperty("departure")
    private String departure;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("departureDate")
    private String departureDate;

    public Criteria(String departure, String destination, String departureDate) {
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Criteria criteria = (Criteria) o;

        if (departure != null ? !departure.equals(criteria.departure) : criteria.departure != null) return false;
        if (departureDate != null ? !departureDate.equals(criteria.departureDate) : criteria.departureDate != null)
            return false;
        if (destination != null ? !destination.equals(criteria.destination) : criteria.destination != null)
            return false;

        return true;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(departureDate.trim());
            System.out.println(date);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return departureDate.replaceAll("/", "-").trim();
    }

    public String cacheKey() {
        return departure.trim() + destination.trim() + getDepartureDate();
    }

    @Override
    public int hashCode() {
        int result = departure != null ? departure.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (departureDate != null ? departureDate.hashCode() : 0);
        return result;
    }
}
