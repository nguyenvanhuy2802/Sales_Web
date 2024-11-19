package org.example.servlet;


import org.example.DAO.CartDAO;
import org.example.DAO.UserDAO;
import org.example.model.Cart;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String profile_image = request.getParameter("profile_image");

        UserDAO userDAO = new UserDAO();

        List<User> userList = null;
        userList = userDAO.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) ;
            request.setAttribute("message", "Tên đăng nhập đã tồn tại.");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            break;
        }

        // Tạo người dùng, giỏ hàng
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setProfileImage(profile_image);

        // Thêm người dùng
        userDAO.addUser(newUser);

        CartDAO cartDAO = new CartDAO();
        Cart newCart = new Cart();
        newCart.setCustomerId(newUser.getUserId());
        cartDAO.addCart(newCart);

        // Chuyển hướng tới trang đăng nhập
        response.sendRedirect(request.getContextPath() + "views/user/login.jsp");
    }
}

