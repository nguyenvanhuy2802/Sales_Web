package org.example.servlet;


import org.example.DAO.UserDAO;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirect = req.getParameter("redirect");
        String id = req.getParameter("id");

        req.setAttribute("redirect", redirect);
        req.setAttribute("id", id);

        req.getRequestDispatcher("views/user/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        List<User> userList;
        userList = userDAO.getAllUsers();
        User currentUser = null;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
            }
        }
        if (currentUser != null && currentUser.getPassword().equals(password)) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("user", currentUser);
            if(currentUser.getRole().equalsIgnoreCase("admin")){
//                response.sendRedirect(request.getContextPath() + "admin");
                request.getRequestDispatcher("views/admin/dashboard.jsp").forward(request, response);

            }
            // Xử lý tham số redirect
            String redirect = request.getParameter("redirect");
            String id = request.getParameter("id");
            if (redirect != null) {
                String redirectURL = switch (redirect) {
                    case "cart" -> "/cart";
                    case "orders" -> "/orders";
                    case "buyNow" -> "/buyNow?id=" + URLEncoder.encode(id, "UTF-8");
                    case "addToCart" -> "/addToCart?id=" + URLEncoder.encode(id, "UTF-8");
                    default -> "/product";
                };
                response.sendRedirect(request.getContextPath() + redirectURL);
            } else {
                response.sendRedirect(request.getContextPath() + "/product");
            }
        } else {
            // Đăng nhập thất bại
            request.setAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("views/user/login.jsp").forward(request, response);
        }
    }
}
