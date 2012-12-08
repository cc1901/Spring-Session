package springWeb.service;

import com.google.common.base.Strings;
import springWeb.domain.ChatResponse;

public class ChatService {
    private ChatEngineInterface chatEngine;

    public ChatService(ChatEngineInterface chatEngine) {
        this.chatEngine = chatEngine;
    }

    public String chat(String input, String context) {
        return chatEngine.chatWithEngine(input, preProcessContext(context));
    }

    public ChatResponse chat1(String input, String context) {
        return chatEngine.chatWithEngine1(input, preProcessContext(context));
    }

    private String preProcessContext(String context) {
        return Strings.nullToEmpty(context).replaceAll("\\n+", "").replaceAll("\\n\\r+", "").replaceAll("\\r+", "").replaceAll("\\s+", "");
    }
}
