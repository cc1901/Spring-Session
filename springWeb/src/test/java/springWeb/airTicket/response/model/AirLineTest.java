package springWeb.airTicket.response.model;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AirLineTest {
    @Test
    public void should_get_min_price(){
        AirLine airLine = new AirLine();
        airLine.setCabins(Lists.newArrayList(new Cabin("600.0"), new Cabin("700.0")));
        airLine.calculatePrice();
        assertThat(airLine.getPrice(), is("600.0"));
    }
}
