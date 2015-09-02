package ua.servlets.ChatJEE.ChatServer;

/**
 * Created by Java Developer on 01.09.2015.
 */
public class SessionListTest {
    public static void main(String[] args) {
        String ID1 = SessionList.getInstance().addLogin("Ivan");
        System.out.println(ID1);
        System.out.println(SessionList.getInstance().addLogin("Ivan"));

        String ID2 = SessionList.getInstance().addLogin("Vanya");

        System.out.println(SessionList.getInstance().getLoginByID(ID1));
        System.out.println(SessionList.getInstance().getLoginByID(ID2));
    }
}
