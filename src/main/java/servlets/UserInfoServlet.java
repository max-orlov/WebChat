package servlets;

import dataBase.DAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF8");
        if(req.getParameter("id") != null) {
             String id = req.getParameter("id");
             User user = (User) DAO.getObjectById(Long.valueOf(id), User.class);
             resp.getWriter().write(user.toString());
        }
        else if(req.getParameter("name") != null) {
            String name  = req.getParameter("name");
            User user = (User) DAO.getObjectByParam("name", name, User.class);
            resp.getWriter().write(user.toString());
        }
    }
}
