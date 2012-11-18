package springWeb.controller;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.junit.Test;
import springWeb.airTicket.response.model.AirLine;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TicketAnswerTest {
    @Test
    public void should_to_json() throws IOException {
        TicketAnswer ticketAnswer = new TicketAnswer(Lists.<AirLine>newArrayList(), "user answer");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().setAnnotationIntrospector(new JacksonAnnotationIntrospector());
        assertThat(mapper.writeValueAsString(ticketAnswer), is("{\"airLines\":[],\"userAnswer\":\"user answer\"}"));
    }
}
