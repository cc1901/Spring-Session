package springWeb.view;

import org.junit.Test;

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
}
