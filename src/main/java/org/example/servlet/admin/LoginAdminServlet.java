package org.example.servlet.admin;


import org.example.DAO.UserDAO;
import org.example.model.User;
import org.example.utils.RSAUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/loginAdmin")
public class LoginAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/admin/adminLogin.jsp").forward(req, resp);
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
        if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
            try {
                // Giải mã mật khẩu được lưu trong cơ sở dữ liệu bằng RSA
                String decryptedPassword = RSAUtil.decrypt(currentUser.getPassword(), RSAUtil.getPrivateKey());

                if (decryptedPassword.equals(password)) {
                    // Đăng nhập thành công
                    HttpSession session = request.getSession();
                    session.setAttribute("admin", currentUser);
                    response.sendRedirect(request.getContextPath() + "/homeAdmin");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Lỗi xử lý mật khẩu. Vui lòng thử lại.");
                request.getRequestDispatcher("views/admin/adminLogin.jsp").forward(request, response);
                return;
            }
        }

        // Đăng nhập thất bại
        request.setAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng.");
        request.getRequestDispatcher("views/admin/adminLogin.jsp").forward(request, response);
    }
}