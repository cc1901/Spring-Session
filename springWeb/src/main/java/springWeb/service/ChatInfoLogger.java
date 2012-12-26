package springWeb.service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.apache.ApacheHttpClient;
import springWeb.util.Client;
import springWeb.util.FullDateTimeFormator;
import springWeb.util.Utf8Logger;

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
            new Utf8Logger().printLog("logger answer--------------------");
            String answerInJson = stringInJson(answer);
            String questionInJson = stringInJson(question);
            new Utf8Logger().printLog(answerInJson);
            new Utf8Logger().printLog(questionInJson);
            new Utf8Logger().printLog("logger answer--------------------");
            String userInfoJson = getUserInfoJson(sessionId, ip, questionInJson, answerInJson, dateTime);
            new Utf8Logger().printLog("user info json:===" + userInfoJson);
            ApacheHttpClient client = new Client().createRESTFulClient();
            ClientResponse response = client.resource(logServerUrl).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, userInfoJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String stringInJson(String answer) {
        return answer.replace("\"", "\\\"");
    }

    private String getUserInfoJson(String sessionId, String ip, String question, String answer, Date dateTime) {
        return String.format(USER_INFO_JSON_TEMPLATE, ip, sessionId, question, answer, FullDateTimeFormator.format(dateTime));
    }

}
