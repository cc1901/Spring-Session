package springWeb.service;

import springWeb.domain.ChatResponse;

public interface ChatEngineInterface {
    String chatWithEngine(String input, String context);
    ChatResponse chatWithEngine1(String input, String context);
}
