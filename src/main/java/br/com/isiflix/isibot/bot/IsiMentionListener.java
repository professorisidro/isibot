package br.com.isiflix.isibot.bot;

import br.com.isiflix.isibot.services.IsiAssistant;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class IsiMentionListener extends ListenerAdapter {

	private IsiAssistant assistant;

	public IsiMentionListener(IsiAssistant assistant) {
		this.assistant = assistant;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		
		System.out.println("Recebi....");

		
		String content = event.getMessage().getContentRaw();
		String mention = "<@" + event.getJDA().getSelfUser().getId() + ">";
		String mentionNick = "<@!" + event.getJDA().getSelfUser().getId() + ">";
		String author = event.getAuthor().getName();
		System.out.println("DEBUG - "+content); 
		System.out.println("DEBUG - "+mention); 
		System.out.println("DEBUG - "+mentionNick); 
		System.out.println("DEBUG - "+event.getJDA().getSelfUser().getName());
		System.out.println("DEBUG - "+event.getAuthor().getName()); 
		
		
		if (content.contains(mention) || content.contains(mentionNick)) {
			System.out.println("Recebido mensagem de: "+event.getAuthor().getName()+ " para "+event.getJDA().getSelfUser().getName());
			String resposta = assistant.answer(event.getMessage().getContentDisplay());
			System.out.println(resposta);
			event.getChannel().sendMessage(resposta).queue();
//			
		}
	}
}
