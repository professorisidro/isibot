package br.com.isiflix.isibot.bot;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.com.isiflix.isibot.services.IsiAssistant;
import br.com.isiflix.isibot.utils.Utils;
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

//		System.out.println("Recebi....");
//
//		String content = event.getMessage().getContentRaw();
//		String mention = "<@" + event.getJDA().getSelfUser().getId() + ">";
//		String mentionNick = "<@!" + event.getJDA().getSelfUser().getId() + ">";
//		String author = event.getAuthor().getName();
//		System.out.println("DEBUG - " + content);
//		System.out.println("DEBUG - " + event.getJDA().getSelfUser().getName());
//		System.out.println("DEBUG - " + event.getAuthor().getName());

		// if (content.contains(mention) || content.contains(mentionNick)) {
//		System.out.println("Recebido mensagem de: " + event.getAuthor().getName() + " para "
//				+ event.getJDA().getSelfUser().getName());
		
		
		// verify attachments
		StringBuilder str = new StringBuilder();
		if (!event.getMessage().getAttachments().isEmpty()) {
			event.getMessage().getAttachments().stream().forEach(attachment -> {
				System.out.println("Achei um anexo: " + attachment.getFileName() + "." + attachment.getFileExtension());
				try {
					// Faz o download de forma bloqueante
					InputStream inputStream = attachment.getProxy().download().join();
					try (InputStream in = inputStream) {
						byte[] btContent = in.readAllBytes();
						String fileContent = new String(btContent);
						str.append(fileContent).append("\n-------------------------------");
					}
				} catch (IOException e) {
					System.err.println("Erro ao ler o anexo " + attachment.getFileName() + ": " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					System.err.println("Erro ao baixar o anexo " + attachment.getFileName() + ": " + e.getMessage());
					e.printStackTrace();
				}
			});
		}
//		System.out.println("DEBUG = " + str.toString());
		String resposta = assistant.answer(event.getMessage().getContentDisplay() + " " + str.toString());

		
		List<String> respostas = Utils.splitMessage(resposta);

		respostas.stream().forEach(resp -> event.getChannel().sendMessage(resp).queue());
		
		
		// }
	}

	public void handleCommand(String content, MessageReceivedEvent event) {

	}
}
