package br.com.isiflix.isibot.services;

import dev.langchain4j.service.SystemMessage;

@SystemMessage(value = "")
public interface IsiAssistant {
	public String answer(String question);

}
