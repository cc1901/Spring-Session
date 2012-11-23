package springWeb.airTicket;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CriteriaTest {
    @Test
    public void should_parse_criteria() throws IOException {
//        String criteriaString = "{\"departure\":\"PEK\",\"destination\":\"Xi'an\",\"departureDate\":\"2012-12-13\"}";
        String criteriaString = "{\"departure\":\"PEK\",\"departureDate\":\"2012-11-21\",\"destination\":\"SHA\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Criteria criteria = objectMapper.readValue(criteriaString, Criteria.class);
        assertThat(criteria.getDeparture(), is("PEK"));
    }
}
