package springWeb.airTicket;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class TicketQueryParser {

    public TicketQuery parse(String queryString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TicketQuery ticketQuery = objectMapper.readValue(queryString, TicketQuery.class);
        return ticketQuery;
    }

}
