package springWeb.airTicket;

import org.springframework.cache.annotation.Cacheable;
import springWeb.airTicket.response.model.TicketQueryResponse;
import springWeb.util.XmlParser;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TicketInformationFetcher {

    public static final String PASSWORD = "jpptopa";

    public static void main(String[] args) {
        new TicketInformationFetcher().fetch(new Criteria("PEK", "SHA", "2012-11-03"));
    }

    @Cacheable(value = "fetchTicketsInfo", key = "#criteria.cacheKey()")
    public TicketQueryResponse fetch(Criteria criteria) {
        try {
            SecretKey secretKey = new SecretKeySpec(PASSWORD.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            String requestXml = String.format("<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<FlightSearchRequest>\n" +
                    "  <TravelType>Single</TravelType> \n" +
                    "  <TravelSummaryCollection>\n" +
                    "    <TravelSummary>\n" +
                    "      <OrgCode>%s</OrgCode> \n" +
                    "      <DstCode>%s</DstCode> \n" +
                    "      <OrgDate>%s</OrgDate>\n" +
                    "      <Interval>06:00-18:00</Interval>\n" +
                    "    </TravelSummary>\n" +
                    "  </TravelSummaryCollection>\n" +
                    "  <Airline /> \n" +
                    "  <CabinLeval /> \n" +
                    "  <DiscountSelect /> \n" +
                    "</FlightSearchRequest>\n", criteria.getDeparture(), criteria.getDestination(), criteria.getDepartureDate());
            String requestHash = new sun.misc.BASE64Encoder().encode(mac.doFinal(requestXml.getBytes("UTF-8")));
            System.out.println(requestHash);
            URL url = new URL("http://coservice.9588.com/default.aspx");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());


            StringBuilder urlParameterBuilder = new StringBuilder();
            urlParameterBuilder.append("version=" + URLEncoder.encode("1.0.0", "UTF-8"));
            urlParameterBuilder.append("&action=" + URLEncoder.encode("FlightSearch", "UTF-8"));
            urlParameterBuilder.append("&user=" + URLEncoder.encode("3015", "UTF-8"));
            urlParameterBuilder.append("&reqxml=" + URLEncoder.encode(requestXml, "UTF-8"));
            urlParameterBuilder.append("&reqhash=" + URLEncoder.encode(requestHash, "UTF-8"));
            wr.write(urlParameterBuilder.toString().getBytes());
            wr.flush();
            PrintStream out = new PrintStream(System.out, true, "UTF-8");
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                out.println(line);
            }
            out.close();
            rd.close();
            wr.close();
            httpURLConnection.disconnect();
            XmlParser<TicketQueryResponse> parser = new XmlParser<TicketQueryResponse>();
            TicketQueryResponse ticketQueryResponse = parser.parse(response.toString(), TicketQueryResponse.class);
            return ticketQueryResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Cacheable(value = "fetchTicketsInfo", key = "#criteria.cacheKey()")
    public String fetchTest(Criteria criteria) {
        System.out.println("----------------------------------------");
        return "test";
    }
}
