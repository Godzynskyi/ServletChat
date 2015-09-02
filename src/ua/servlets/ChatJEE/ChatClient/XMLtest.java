package ua.servlets.ChatJEE.ChatClient;

import ua.servlets.ChatJEE.ChatServer.*;
import ua.servlets.ChatJEE.ChatClient.Message;

import javax.xml.bind.JAXBException;
import java.util.Date;

/**
 * Created by Java Developer on 02.09.2015.
 */
public class XMLtest {
    public static void main(String[] args) throws JAXBException {
        ua.servlets.ChatJEE.ChatClient.Message msg = new Message();
        msg.setText("text");
        msg.setDate(new Date());
        msg.setFrom("Ivan");

        String xml = msg.toXML();
        System.out.println(xml);
        Message message = Message.fromXML(xml);
        System.out.println(message.getDate());
        System.out.println(message.getFrom());
        System.out.println(message.getText());
        System.out.println(message.getTo());
    }
}
