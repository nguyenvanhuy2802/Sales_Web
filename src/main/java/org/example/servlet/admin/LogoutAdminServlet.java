package org.example.servlet.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logoutAdmin")
public class LogoutAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Lấy session hiện tại mà không tạo mới
        if(session != null){
            session.invalidate(); // Hủy session
        }
        response.sendRedirect(request.getContextPath() + "/views/admin/adminLogin.jsp");
    }
}
