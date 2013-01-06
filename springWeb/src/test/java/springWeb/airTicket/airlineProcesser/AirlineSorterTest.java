package springWeb.airTicket.airlineProcesser;

import com.google.common.collect.Lists;
import org.junit.Test;
import springWeb.airTicket.TicketQuery;
import springWeb.airTicket.response.model.AirLine;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AirlineSorterTest {
    @Test
    public void should_sort_airlines(){
        TicketQuery ticketQuery = new TicketQuery();
        setFieldOfObject(ticketQuery, "sortBy", "price");
        List<AirLine> airLines = Lists.newArrayList();
        AirLine airLine1 = new AirLine();
        AirLine airLine2 = new AirLine();
        setFieldOfObject(airLine1, "price", "200.0");
        setFieldOfObject(airLine2, "price", "100.0");
        airLines.add(airLine1);
        airLines.add(airLine2);
        AirlineSorter.sort(airLines, ticketQuery);

        assertThat(airLines.get(0).getPrice(), is("100.0"));
    }

    private void setFieldOfObject(Object object, String field, String fieldValue) {
        try {
            Field sortBy = object.getClass().getDeclaredField(field);
            sortBy.setAccessible(true);
            sortBy.set(object, fieldValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
