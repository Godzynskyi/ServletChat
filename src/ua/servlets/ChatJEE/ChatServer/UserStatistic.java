package ua.servlets.ChatJEE.ChatServer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Java Developer on 02.09.2015.
 */
public class UserStatistic {
    static class Statistic {
        Date RegistrationTime;
        int countOfMessages;
        int countOfPrivateMessages;

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("[ " + RegistrationTime.toString() + " ] ");
            result.append("Sent messages: " + countOfMessages);
            result.append(" Sent private messages: " + countOfPrivateMessages);
            return result.toString();
        }
    }

    private static Map<String, Statistic> userStatistic = new HashMap<String, Statistic>();

    public static void addUser(String id) {
        Statistic user = new Statistic();
        user.countOfMessages=0;
        user.countOfPrivateMessages=0;
        user.RegistrationTime=new Date();

        userStatistic.put(id,user);
    }

    public static void userSentMessage(String id, Message msg) {
        Statistic user = userStatistic.get(id);
        if(msg.getTo()!=null) user.countOfPrivateMessages++;
        user.countOfMessages++;
//        userStatistic.put(id,user);
    }

    public static String getStatistic(String id) {
        return userStatistic.get(id).toString();
    }

    public static String getStatistic() {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, Statistic> entry: userStatistic.entrySet()) {
            result.append(entry.getKey() + ": " + getStatistic(entry.getKey()) + "\r\n");
        }
        return result.toString();
    }
}
