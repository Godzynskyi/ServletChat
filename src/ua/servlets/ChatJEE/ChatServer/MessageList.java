package ua.servlets.ChatJEE.ChatServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
	
	private static final MessageList msgList = new MessageList();

	private final List<Message> list = new ArrayList<Message>();
	
	public static MessageList getInstance() {
		return msgList;
	}
	
	public synchronized void add(Message m) {
		if("".equals(m.getFrom())) return;
		list.add(m);
	}
	
	public synchronized String toJSON(int n) {
		List<Message> res = new ArrayList<Message>();
		for (int i = n; i < list.size(); i++)
			res.add(list.get(i));
		
		if (res.size() > 0) {
			Gson gson = new GsonBuilder().create();
			return gson.toJson(res.toArray());
		} else
			return null;
	}

	public synchronized String toJSON(int n, int id) {
		List<Message> res = new ArrayList<Message>();
		for (int i = n; i < list.size(); i++) {
			Message message = list.get(i);
			if(message.getTo()==null
					|| message.getTo().equals(SessionList.getInstance().getLoginByID(String.valueOf(id)))
					|| message.getFrom().equals(SessionList.getInstance().getLoginByID(String.valueOf(id))))
				res.add(list.get(i));
		}
		if (res.size() > 0) {
			Gson gson = new GsonBuilder().create();
			return gson.toJson(res.toArray());
		} else
			return null;
	}
}
