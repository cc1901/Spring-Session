package springWeb.airTicket;

import java.util.List;

public class TicketQuery {
    private String sortBy;
    private List<String> filter;

    private Criteria criteria = new Criteria();

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
