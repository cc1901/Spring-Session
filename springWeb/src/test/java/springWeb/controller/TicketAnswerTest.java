package springWeb.controller;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.junit.Test;
import springWeb.airTicket.response.model.AirLine;
import springWeb.domain.TicketAnswer;
import springWeb.helper.AirLineViewsHelper;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TicketAnswerTest {
    @Test
    public void should_to_json() throws IOException {
        TicketAnswer ticketAnswer = new TicketAnswer(AirLineViewsHelper.transferAirLineView(Lists.<AirLine>newArrayList()), "user answer prefix", "user answer suffix");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().setAnnotationIntrospector(new JacksonAnnotationIntrospector());
        assertThat(mapper.writeValueAsString(ticketAnswer), is("{\"airLines\":[],\"userAnswerPrefix\":\"user answer prefix\",\"userAnswerSuffix\":\"user answer suffix\"}"));
    }
}