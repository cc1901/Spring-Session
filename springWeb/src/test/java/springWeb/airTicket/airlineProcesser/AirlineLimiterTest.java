package springWeb.airTicket.airlineProcesser;

import com.google.common.collect.Lists;
import org.junit.Test;
import springWeb.airTicket.response.model.AirLine;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AirlineLimiterTest {
    @Test
    public void should_limit_airline_to_5_when_more_than_5_airlines() {
        List<AirLine> airLines = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            airLines.add(new AirLine());
        }
        List<AirLine> limitAirlines = AirlineLimiter.limit(airLines);
        assertThat(limitAirlines.size(), is(5));
    }

    @Test
    public void should_not_limit_airline_when_less_than_5_airlines() {
        List<AirLine> airLines = Lists.newArrayList();
        for (int i = 0; i < 4; i++) {
            airLines.add(new AirLine());
        }
        List<AirLine> limitAirlines = AirlineLimiter.limit(airLines);
        assertThat(limitAirlines.size(), is(4));
    }
}
