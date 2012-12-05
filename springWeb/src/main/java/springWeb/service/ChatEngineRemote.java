package springWeb.service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.apache.ApacheHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import springWeb.util.ChatAnswer;
import springWeb.util.Client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import java.io.IOException;
import java.util.List;

public class ChatEngineRemote implements ChatEngineInterface {
    @Override
    public String chatWithEngine(String input, String context) {
        ApacheHttpClient client = new Client().createRESTFulClient();
        ClientResponse response = client.resource("http://192.168.15.108:9000/chat").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, String.format("{\"question\": \"%s\"}", input));
        List<NewCookie> cookies = response.getCookies();
        System.out.println(cookies.get(0).toString());
        String answer = response.getEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ChatAnswer chatAnswer = new ChatAnswer();
        try {
            chatAnswer = objectMapper.readValue(answer, ChatAnswer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chatAnswer.getAnswer();
    }

    public static void main(String[] args) throws IOException {
        String answer = new ChatEngineRemote().chatWithEngine("你好", "");

        System.out.println(answer + "--------------");
    }

}

