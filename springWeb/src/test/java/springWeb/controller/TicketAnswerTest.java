package springWeb.controller;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.junit.Test;
import springWeb.airTicket.response.model.AirLine;
import springWeb.airTicket.response.model.Cabin;
import springWeb.view.AirLineView;
import springWeb.view.TicketAnswer;
import springWeb.helper.AirLineViewsHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TicketAnswerTest {
    @Test
    public void should_to_json() throws IOException {
        AirLineView airLineView = new AirLineView(Lists.<Cabin>newArrayList(), "350.0", "123", "2012-12-23T06:35:00", "2012-12-23T09:00:00", "HTO", "XI");
        List<AirLineView> airLineViews = Lists.newArrayList(airLineView);
        TicketAnswer ticketAnswer = new TicketAnswer(airLineViews, "回答前缀", "回答后缀");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().setAnnotationIntrospector(new JacksonAnnotationIntrospector());
        assertThat(mapper.writeValueAsString(ticketAnswer), is("{\"airLines\":[{\"cabins\":[],\"price\":\"350元\",\"flightNumber\":\"123\",\"arriveDate\":\"2012-12-23 09:00\",\"departureDate\":\"2012-12-23 06:35\",\"orgAirport\":\"HTO\",\"dstAirport\":\"XI\"}],\"userAnswerPrefix\":\"回答前缀\",\"userAnswerSuffix\":\"回答后缀\"}"));
    }
}