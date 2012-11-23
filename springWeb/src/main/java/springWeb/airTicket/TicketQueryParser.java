package springWeb.airTicket;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class TicketQueryParser {

    public TicketQuery parse(String queryString) throws IOException {
        org.codehaus.jackson.map.ObjectMapper objectMapper = new org.codehaus.jackson.map.ObjectMapper();
        TicketQuery ticketQuery = objectMapper.readValue(queryString, TicketQuery.class);
        return ticketQuery;
    }

}
