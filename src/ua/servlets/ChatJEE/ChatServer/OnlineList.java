package ua.servlets.ChatJEE.ChatServer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Java Developer on 01.09.2015.
 */
public class OnlineList {
    private static OnlineList onlineList = new OnlineList();

    public static OnlineList getInstance() {
        return onlineList;
    }

    private OnlineList() {}

    private Map<String, Long> map = new HashMap<String, Long>();

    public void wasActive(String id) {
        map.put(id, System.currentTimeMillis());
    }

    public List<String> getOnlineID() {
        List<String> result = new LinkedList<String>();
        for(Map.Entry<String, Long> entry: map.entrySet()) {
            if(System.currentTimeMillis() - entry.getValue() < 3000) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
