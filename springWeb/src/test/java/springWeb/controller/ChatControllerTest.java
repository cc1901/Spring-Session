package springWeb.controller;

import com.google.common.base.Splitter;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatControllerTest {
    @Test
    public void should_() {
        String s = "jni test<ChatStateContainer>abc</ChatStateContainer>";
        int i = s.indexOf("<ChatStateContainer>");
        System.out.println(s.substring(0, i));
        System.out.println(s.substring(i));
    }

    @Test
    public void should_parse_date() throws ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date date = dateFormat.parse("2012-11-03T06:35:00");
//        System.out.println(date.toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2012-11-03");
        System.out.println(date.toString());
    }

    @Test
    public void should_encrypt() {
        try {
            byte[] bkey = new BASE64Decoder().decodeBuffer("jpptopa");
            SecretKey secretKey = new SecretKeySpec(bkey, "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            String requestXml = "<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<FlightSearchRequest>\n" +
                    "  <TravelType>Single</TravelType> \n" +
                    "  <TravelSummaryCollection>\n" +
                    "    <TravelSummary>\n" +
                    "      <OrgCode>PEK</OrgCode> \n" +
                    "      <DstCode>SHA</DstCode> \n" +
                    "      <OrgDate>2010-12-28</OrgDate>\n" +
                    "      <Interval>06:00-12:00</Interval>\n" +
                    "    </TravelSummary>\n" +
                    "  </TravelSummaryCollection>\n" +
                    "  <Airline /> \n" +
                    "  <CabinLeval /> \n" +
                    "  <DiscountSelect /> \n" +
                    "</FlightSearchRequest>\n";
            String s = mac.doFinal(requestXml.getBytes()).toString();
            System.out.println(s);
            URL url = new URL("http://coservice.9588.com/default.aspx");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            String encode = URLEncoder.encode("version=1.0.0&action=FlightSearch&user=3015&reqxml=" + URLEncoder.encode(requestXml, "UTF-8") + "&reqhash=[B@5ef4f44a", "UTF-8");


            StringBuilder urlParameterBuilder = new StringBuilder();
            urlParameterBuilder.append("version=" + URLEncoder.encode("1.0.0", "UTF-8"));
            urlParameterBuilder.append("&action=" + URLEncoder.encode("FlightSearch", "UTF-8"));
            urlParameterBuilder.append("&user=" + URLEncoder.encode("3015", "UTF-8"));
            urlParameterBuilder.append("&reqxml=" + URLEncoder.encode(requestXml, "UTF-8"));
            urlParameterBuilder.append("&reqhash=" + URLEncoder.encode("[B@5ef4f44a", "UTF-8"));
            wr.write(urlParameterBuilder.toString().getBytes());
            wr.flush();
//            httpURLConnection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            rd.close();
            wr.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
