package springWeb.airTicket;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class TicketQuery {
    @JsonProperty("criteria")
    private Criteria criteria = new Criteria();

    @JsonProperty("sortBy")
    private String sortBy;

    @JsonProperty("filter")
    private List<String> filter;

    public Criteria getCriteria() {
        return criteria;
    }

    public String getSortBy() {
        return sortBy;
    }

    public List<String> getFilter() {
        return filter;
    }
}
