
import Handlers.MessageHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(System.getenv("TOKEN"))
                .setActivity(Activity.playing("Calculator"))
                .addEventListeners(new MessageHandler());
        JDA jda = builder.build();


        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jda.getCategories().get(1)
                .getTextChannels().get(0)
                .sendMessage("TXT")
                .timeout(5, TimeUnit.SECONDS)
                .submit();




    }
}
