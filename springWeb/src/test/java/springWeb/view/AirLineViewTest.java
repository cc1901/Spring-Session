package springWeb.view;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.junit.Test;
import springWeb.airTicket.response.model.Cabin;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AirLineViewTest {
    @Test
    public void should_format_airline_date() {
        AirLineView airLineView = new AirLineView(null, "350.0", null, "2012-12-23T06:35:00", "2012-12-23T09:00:00", null, null);
        assertThat(airLineView.getDepartureDate(), is("2012-12-23 06:35"));
        assertThat(airLineView.getArriveDate(), is("2012-12-23 09:00"));
    }

    @Test
    public void should_format_airline_price() {
        AirLineView airLineView = new AirLineView(null, "300.0", null, null, null, null, null);
        assertThat(airLineView.getPrice(), is("300元"));
    }

    @Test
    public void should_to_json() throws IOException {
        AirLineView airLineView = new AirLineView(Lists.<Cabin>newArrayList(), "350.0", "123", "2012-12-23T06:35:00", "2012-12-23T09:00:00", "HTO", "XI");
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().setAnnotationIntrospector(new JacksonAnnotationIntrospector());
        assertThat(mapper.writeValueAsString(airLineView),
         is("{\"cabins\":[],\"price\":\"350元\",\"flightNumber\":\"123\",\"arriveDate\":\"2012-12-23 09:00\",\"departureDate\":\"2012-12-23 06:35\",\"orgAirport\":\"HTO\",\"dstAirport\":\"XI\"}"
         ));
    }

    @Test
    public void should_() {
        String s = "2012-12-23 06:35".replaceAll(" .*", "");
        System.out.println(s);
    }
}
