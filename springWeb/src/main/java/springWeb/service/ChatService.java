package springWeb.service;

import com.google.common.base.Strings;

public class ChatService {
    private ChatEngine chatEngine;

    public ChatService(ChatEngine chatEngine) {
        this.chatEngine = chatEngine;
    }

    public String chat(String input, String context) {
        return chatEngine.chatWithEngine(input, preProcessContext(context));
    }

    private String preProcessContext(String context) {
        return Strings.nullToEmpty(context).replaceAll("\\n+", "").replaceAll("\\n\\r+", "").replaceAll("\\r+", "").replaceAll("\\s+", "");
    }
}
