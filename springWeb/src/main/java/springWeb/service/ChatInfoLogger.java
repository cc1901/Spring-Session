package springWeb.service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import springWeb.util.FullDateTimeFormator;

import javax.ws.rs.core.MediaType;
import java.util.Date;

public class ChatInfoLogger {
    public static final String USER_INFO_JSON_TEMPLATE = "{\"ip\": \"%s\", \"sessionId\": \"%s\", \"question\": \"%s\", \"answer\": \"%s\", \"time\": \"%s\"}";
    private String logServerUrl;

    public ChatInfoLogger(String logServerUrl) {
        this.logServerUrl = logServerUrl;
    }

    public void logChatHistoryInfo(String sessionId, String ip, String question, String answer, Date dateTime) {
        try {
            String userInfoJson = getUserInfoJson(sessionId, ip, question, answer, dateTime);
            ApacheHttpClient client = createRESTFulClient();
            ClientResponse response = client.resource(logServerUrl).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, userInfoJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUserInfoJson(String sessionId, String ip, String question, String answer, Date dateTime) {
        return String.format(USER_INFO_JSON_TEMPLATE, ip, sessionId, question, answer, FullDateTimeFormator.format(dateTime));
    }

    private ApacheHttpClient createRESTFulClient() {
        ClientConfig clientConfig = new DefaultApacheHttpClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        return ApacheHttpClient.create(clientConfig);
    }
}
