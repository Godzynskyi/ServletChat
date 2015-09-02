package ua.servlets.ChatJEE.ChatServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Java Developer on 01.09.2015.
 */
public class GetOnlineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> listID = OnlineList.getInstance().getOnlineID();

        PrintWriter writer = resp.getWriter();
        for(String id: listID) {
            writer.println(SessionList.getInstance().getLoginByID(id));
        }

        writer.flush();

    }
}
