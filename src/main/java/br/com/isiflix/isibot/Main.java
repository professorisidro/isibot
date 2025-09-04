package br.com.isiflix.isibot;

import br.com.isiflix.isibot.bot.IsiMentionListener;
import br.com.isiflix.isibot.services.IsiAssistant;
import br.com.isiflix.isibot.services.RagService;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws Exception {
    	
        String token = System.getenv("BOT_TOKEN");
        
        RagService service = new RagService();
        IsiAssistant assistant = service.doRag();
        

        JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("mencione @IsiBot!"))
                .addEventListeners(new IsiMentionListener(assistant))
                .build();
    }
}
