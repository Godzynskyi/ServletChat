package ua.servlets.ChatJEE.ChatServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class GetListServlet extends HttpServlet {
	
	private MessageList msgList = MessageList.getInstance();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException 
	{
//		System.out.println("GetList: GET.");
		String fromStr = req.getParameter("from");
		int from = Integer.parseInt(fromStr);
		String idStr = req.getParameter("id");
		String json;
		if(idStr==null) {
			json = msgList.toJSON(from);
		} else {
			int id = Integer.parseInt(idStr);
			OnlineList.getInstance().wasActive(idStr);
			json = msgList.toJSON(from, id);
		}


		if (json != null) {
			OutputStream os = resp.getOutputStream();
			os.write(json.getBytes());
		}
	}
}
