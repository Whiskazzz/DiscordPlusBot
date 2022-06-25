package Handlers;

import DAO.CustomerDAO;
import DAO.CustomerDAOImpl;
import model.Customer;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MessageHandler extends ListenerAdapter {
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Message message = event.getMessage();
//        event.getChannel() == event.getJDA().getTextChannels().get(0)
        if ( message.getContentDisplay().equals("+")
                && message.getMessageReference() != null) {
            User referenceAuthor = message.getMessageReference().getMessage().getAuthor();
            User plusAuthor = message.getAuthor();
            Customer customer;
            if (!customerDAO.isExist(referenceAuthor.getIdLong())){
                customer = new Customer(referenceAuthor.getName(),1l, referenceAuthor.getAsTag(), referenceAuthor.getIdLong());
                customerDAO.addCustomer(customer);
            } else {
                customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                System.out.println(customer.getPlusCount());
                customer.setPlusCount(customer.getPlusCount()+1L);
            }
            if (customer.getPlusCount() == 40) {
                event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                                + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг - " + customer.getPlusCount()
                                + ". Партия выделила тебе одна кошка жена.")
                        .addFile(new File("src/main/resources/img/zhena.jpg")).submit();

            } else if (customer.getPlusCount() == 15) {
                event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                        + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг - " + customer.getPlusCount()
                        + ". Партия выделила тебе дополнительный миска риса.")
                        .addFile(new File("src/main/resources/img/ris.jpg")).submit();

            } else {
                event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                        + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг - " + customer.getPlusCount()
                        + ". Партия гордится тобой.").submit();
            }


        }
    }
}
