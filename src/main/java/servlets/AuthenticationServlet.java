package servlets;

import dataBase.DAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = (User) DAO.getObjectByParam("name", name, User.class);
        if(user == null) {
            resp.getWriter().write("user doesn't exists");
            return;
        }
        if(user.getPassword().equals(password)) {
            Cookie userId = new Cookie("userId", String.valueOf(user.getId()));
            userId.setMaxAge(3600);
            Cookie userName = new Cookie("userName", user.getName());
            userName.setMaxAge(3600);
            resp.addCookie(userId);
            resp.addCookie(userName);
            resp.sendRedirect("chat.html");
        }
        else {
            resp.getWriter().write("incorrect password");
        }
    }
}
