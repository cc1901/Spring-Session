package springWeb.airTicket;

import org.springframework.cache.annotation.Cacheable;
import springWeb.airTicket.response.model.TicketQueryResponse;
import springWeb.util.HashGenerator;
import springWeb.util.XmlParser;

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
        new TicketInformationFetcher().fetch1(new Criteria("PEK", "SHA", "2012-11-03"));
    }

    @Cacheable(value = "fetchTicketsInfo", key = "#criteria.cacheKey()")
    public TicketQueryResponse fetch(Criteria criteria) {
        try {
            System.out.println("start fetch ticket info++++++++++++++++++++++++");
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
                    "      <Interval>00:01-23:59</Interval>\n" +
                    "    </TravelSummary>\n" +
                    "  </TravelSummaryCollection>\n" +
                    "  <Airline /> \n" +
                    "  <CabinLeval /> \n" +
                    "  <DiscountSelect /> \n" +
                    "</FlightSearchRequest>\n", criteria.getDeparture(), criteria.getDestination(), criteria.getDepartureDate());
            String requestHash = new sun.misc.BASE64Encoder().encode(mac.doFinal(requestXml.getBytes("UTF-8")));
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
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            wr.close();
            httpURLConnection.disconnect();
            XmlParser<TicketQueryResponse> parser = new XmlParser<TicketQueryResponse>();
            PrintStream utf8Out = new PrintStream(System.out, true, "UTF-8");
            if (response.length() < 500) {
                utf8Out.println("response string++++++++++++++++++++++++" + response.toString());
            }
            utf8Out.println("response length++++++++++++++++++++++++" + response.length());
            TicketQueryResponse ticketQueryResponse = parser.parse(response.toString(), TicketQueryResponse.class);
            utf8Out.println("successfully fetch ticket info++++++++++++++++++++++++");
            return ticketQueryResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Cacheable(value = "fetchTicketsInfo", key = "#criteria.cacheKey()")
    public TicketQueryResponse fetch1(Criteria criteria) {
        try {
            System.out.println("start fetch ticket info++++++++++++++++++++++++");

            String requestXml = String.format("<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<FlightSearchRequest>\n" +
                    "  <TravelType>Single</TravelType> \n" +
                    "  <TravelSummaryCollection>\n" +
                    "    <TravelSummary>\n" +
                    "      <OrgCode>%s</OrgCode> \n" +
                    "      <DstCode>%s</DstCode> \n" +
                    "      <OrgDate>%s</OrgDate>\n" +
                    "      <Interval>00:01-23:59</Interval>\n" +
                    "    </TravelSummary>\n" +
                    "  </TravelSummaryCollection>\n" +
                    "  <Airline /> \n" +
                    "  <CabinLeval /> \n" +
                    "  <DiscountSelect /> \n" +
                    "</FlightSearchRequest>\n", criteria.getDeparture(), criteria.getDestination(), criteria.getDepartureDate());
            String requestHash = new HashGenerator().generateHash(requestXml);
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
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            wr.close();
            httpURLConnection.disconnect();

            String ticketResponse = response.toString();
            XmlParser<TicketQueryResponse> parser = new XmlParser<TicketQueryResponse>();
            PrintStream utf8Out = new PrintStream(System.out, true, "UTF-8");
            if (response.length() < 500) {
                utf8Out.println("response string++++++++++++++++++++++++" + ticketResponse);
            }
            utf8Out.println("response length++++++++++++++++++++++++" + response.length());
            TicketQueryResponse ticketQueryResponse = parser.parse(ticketResponse, TicketQueryResponse.class);
            utf8Out.println("successfully fetch ticket info++++++++++++++++++++++++");
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
