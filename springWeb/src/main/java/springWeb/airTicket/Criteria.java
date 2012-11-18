package springWeb.airTicket;

public class Criteria {
    public Criteria() {

    }

    private String departure;
    private String destination;
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
