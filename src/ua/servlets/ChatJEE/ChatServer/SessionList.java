package ua.servlets.ChatJEE.ChatServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Java Developer on 01.09.2015.
 */
public class SessionList {
    private static final SessionList sessionList = new SessionList();

    private Map<String, String> map = new HashMap<String, String>();

    public static SessionList getInstance() {
        return sessionList;
    }

    private SessionList() {}

    public String getLoginByID(String id) {
        String result = map.get(id);
        if(result==null) return "";
        return result;
    }

    //Return "-1" if this login have already authorezed
    public String addLogin(String login) {

        for(Map.Entry<String, String> i:map.entrySet()) {
            String log = i.getValue();
            if(log.equals(login)) return "-1";
        }
        Integer id = login.hashCode();
        String sessionId = id.toString();
        map.put(sessionId, login);
        RoomList.enterRoom(sessionId,"ROOT");

        return sessionId;
    }
}
