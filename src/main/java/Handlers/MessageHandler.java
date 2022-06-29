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
//        event.getChannel() == event.getJDA().getTextChannels().get(0) $$
        if ( (event.getChannel() == event.getJDA().getTextChannelsByName("флудерастия", true).get(0)
                || event.getChannel() == event.getJDA().getTextChannelsByName("флудерастия", true).get(1))
                && message.getContentDisplay().equals("+")
                && message.getMessageReference() != null) {
            User referenceAuthor = message.getMessageReference().getMessage().getAuthor();
            User plusAuthor = message.getAuthor();
            Customer customer;
            if (referenceAuthor.isBot()){
                customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Я бот, мы не гонимся за плюсами.").submit();
            } else if (referenceAuthor == plusAuthor) {
                customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Партия не допустит махинаций с плюсами. За вами выехали.").submit();

            } else {
                if (!customerDAO.isExist(referenceAuthor.getIdLong())){
                    customer = new Customer(referenceAuthor.getName(),1l, referenceAuthor.getAsTag(), referenceAuthor.getIdLong());
                    customerDAO.addCustomer(customer);
                } else {
                    customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                    customer.setPlusCount(customer.getPlusCount()+1L);
                    customerDAO.updateCustomer(customer);
                }
                if (customer.getPlusCount() == 40) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                                    + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount()
                                    + ". Партия выделила тебе одна кошка жена.")
                            .addFile(new File("src/main/resources/img/zhena.jpg")).submit();

                } else if (customer.getPlusCount() == 15) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                                    + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount()
                                    + ". Партия выделила тебе дополнительный миска риса.")
                            .addFile(new File("src/main/resources/img/ris.jpg")).submit();

                } else {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия гордится тобой.").submit();
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



        if ( (event.getChannel() == event.getJDA().getTextChannelsByName("флудерастия", true).get(0)
                || event.getChannel() == event.getJDA().getTextChannelsByName("флудерастия", true).get(1))
                && message.getContentDisplay().equals("-")
                && message.getMessageReference() != null) {
            User referenceAuthor = message.getMessageReference().getMessage().getAuthor();
            User plusAuthor = message.getAuthor();
            Customer customer;
            if (referenceAuthor.isBot()){
                customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Я бот, мне плевать на минусы.").submit();

            } else if (referenceAuthor == plusAuthor) {
                customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Партия не допустит махинаций с плюсами. За вами выехали.").submit();

            } else {
                if (!customerDAO.isExist(referenceAuthor.getIdLong())){
                    customer = new Customer(referenceAuthor.getName(),-1L, referenceAuthor.getAsTag(), referenceAuthor.getIdLong());
                    customerDAO.addCustomer(customer);
                } else {
                    customer = customerDAO.getCustomer(referenceAuthor.getIdLong());
                    customer.setPlusCount(customer.getPlusCount()-1L);
                    customerDAO.updateCustomer(customer);
                }
                if (customer.getPlusCount() == 39) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия забирает твою кошкажена в трудовой лагерь.").submit();

                } else if (customer.getPlusCount() == 14) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия забирает дополнительный миска риса.").submit();

                } else if (customer.getPlusCount() == 0){
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия разочарована в тебе.").submit();

                } else {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг - " + customer.getPlusCount()
                            + ". Ваш рейтинг слишком низок, к вам отправлен комендант для транспортировки в трудовой лагерь.").submit();
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
