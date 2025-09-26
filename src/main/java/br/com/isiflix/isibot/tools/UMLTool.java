package br.com.isiflix.isibot.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.chat.ChatModel;

public class UMLTool {

	private ChatModel chatModel;
	
	public UMLTool(ChatModel model) {
		this.chatModel = model;
	}
	
	@Tool("UMLTool")
	public String generateUMLMarkdown(@P("description") String description) {
		String instructions = String.join("\n",
				                     "Você é um conversor de descrições textuais para especificações UML.",
				                     "Converta a descrição fornecida para um modelo UML no formato MARKDOWN compatível com o PlantText.",
				                     "",
				                     "Texto a ser convertido: ",
				                     "%s ",
				                     "\n",
				                     "LEMBRETE: Gere apenas o Markdown. Não inclua nenhum outro comentário na sua resposta")
				                    .formatted(description);
				   
		return chatModel.chat(instructions);
	}

}
