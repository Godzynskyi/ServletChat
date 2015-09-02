package ua.servlets.ChatJEE.ChatServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class AddServlet extends HttpServlet {

//	@Override
//	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String from = req.getParameter("from");
//		String text = req.getParameter("text");
//		Message message = new Message();
//		message.setFrom(from);
//		message.setDate(new Date());
//		message.setText(text);
//		msgList.add(message);
//
//		resp.getWriter().write("OK");
//	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
//		System.out.println("Add server: POST.");
		InputStream is = req.getInputStream();
		byte[] buf = new byte[is.available()];
		is.read(buf);


		Message msg = Message.fromJSON(new String(buf));
		if (msg != null) {
			MessageList msgList = RoomList.getRoomOfUser(msg.getFrom());
			UserStatistic.userSentMessage(msg.getFrom(),msg);
			msg.setFrom(SessionList.getInstance().getLoginByID(msg.getFrom()));
			msgList.add(msg);
		} else {
			resp.setStatus(400); // Bad request
		}
	}
}
