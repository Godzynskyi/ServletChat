package ua.servlets.ChatJEE.ChatServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Java Developer on 02.09.2015.
 */
public class RoomList {

    private static Map<String, MessageList> rooms = new HashMap<String, MessageList>();
    private static Map<String, String> roomOfUser = new HashMap<String, String>();

    {
        MessageList ml = new MessageList();
        rooms.put("ROOT", ml);
    }

    public static boolean addRoom(String name) {
        if (rooms.containsKey(name)) return false;
        rooms.put(name, new MessageList());
        return true;
    }

    public static void enterRoom(String ID, String roomName) {
        addRoom(roomName);
        roomOfUser.put(ID, roomName);
    }

    public static MessageList getRoomOfUser(String ID) {
        return rooms.get(roomOfUser.get(ID));
    }

}
