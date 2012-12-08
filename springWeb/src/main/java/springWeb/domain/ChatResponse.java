package springWeb.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class ChatResponse {
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("context")
    private String context;

    public ChatResponse() {
        this("", "");
    }

    public ChatResponse(String answer, String context) {
        this.answer = answer;
        this.context = context;
    }

    public String getAnswer() {
        return answer;
    }

    public String getContext() {
        return context;
    }
}
