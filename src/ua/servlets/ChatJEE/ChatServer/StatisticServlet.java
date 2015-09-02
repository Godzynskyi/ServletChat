package ua.servlets.ChatJEE.ChatServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Java Developer on 02.09.2015.
 */
public class StatisticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String respSTR;
        if(id==null) respSTR=UserStatistic.getStatistic(); else respSTR=UserStatistic.getStatistic(id);
        resp.getWriter().write(respSTR);
    }
}
