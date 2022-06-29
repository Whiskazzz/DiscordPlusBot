package Handlers;

import DAO.CustomerDAO;
import DAO.CustomerDAOImpl;
import model.Customer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class ComandHandler extends ListenerAdapter {

    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        List<Customer> customers = customerDAO.getStats();
        embedBuilder.setTitle("Доска почета партии");
        embedBuilder.setColor(Color.lightGray);
        embedBuilder.setImage("https://medialeaks.ru/wp-content/uploads/2020/05/94d-369x500.jpg");
        for (int i = 0; i < customers.size(); i++) {
            embedBuilder.addField("", String.valueOf(i+1),true);
            embedBuilder.addField("Name", customers.get(i).getName(), true);
            embedBuilder.addField("Social Rate", String.valueOf(customers.get(i).getPlusCount()), true);
        }


        Message message = event.getMessage();
        if ( (event.getChannel() == event.getJDA().getTextChannelsByName("флудерастия", true).get(0)
                || event.getChannel() == event.getJDA().getTextChannelsByName("флудерастия", true).get(1))
                && message.getContentRaw().equals("/stats")) {
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).submit();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
