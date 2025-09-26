package br.com.isiflix.isibot.services;

public class AssistantResponse {
    private String answer;
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;

    
    public AssistantResponse() {
		super();
	}

	public AssistantResponse(String answer, int promptTokens, int completionTokens, int totalTokens) {
        this.answer = answer;
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPromptTokens() {
        return promptTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public int getTotalTokens() {
        return totalTokens;
    }
}