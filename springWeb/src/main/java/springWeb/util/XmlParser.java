package springWeb.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import springWeb.airTicket.response.model.TicketQueryResponse;

import java.io.IOException;

public class XmlParser<T> {

    public T parse(String xmlContent, Class clazz) throws IOException {
        return (T) new XmlMapper().readValue(xmlContent, clazz);
    }
}
