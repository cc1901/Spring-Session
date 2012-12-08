package springWeb.service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.apache.ApacheHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import springWeb.domain.ChatResponse;
import springWeb.util.ChatAnswer;
import springWeb.util.Client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import java.io.IOException;
import java.util.List;

public class ChatEngineRemote implements ChatEngineInterface {
    private String chatServerUrl = "http://192.168.15.108:9000/chat";

    public void setChatServerUrl(String chatServerUrl) {
        this.chatServerUrl = chatServerUrl;
    }

    @Override
    public String chatWithEngine(String question, String context) {
        ApacheHttpClient client = new Client().createRESTFulClient();
        String chatRequest = String.format("{\"question\": \"%s\",\"context\":\"%s\"}", question, context);
        ClientResponse response = client.resource(chatServerUrl).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, chatRequest);
        ChatResponse chatResponse = response.getEntity(ChatResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ChatAnswer chatAnswer = new ChatAnswer();
        try {
            chatAnswer = objectMapper.readValue("", ChatAnswer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chatAnswer.getAnswer();
    }


    @Override
    public ChatResponse chatWithEngine1(String question, String context) {
        ApacheHttpClient client = new Client().createRESTFulClient();
        String chatRequest = String.format("{\"question\": \"%s\",\"context\":\"%s\"}", question, context);
        ClientResponse response = client.resource(chatServerUrl).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, chatRequest);
        String chatResponse = response.getEntity(String.class);
        System.out.println("chat response:" + chatResponse + "====================");
        ObjectMapper objectMapper = new ObjectMapper();
        ChatResponse chatAnswer = new ChatResponse();
        try {
            chatAnswer = objectMapper.readValue(chatResponse, ChatResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chatAnswer;
    }

    public static void main(String[] args) throws IOException {
        String answer = new ChatEngineRemote().chatWithEngine("你好", "");

        System.out.println(answer + "--------------");
    }

}

