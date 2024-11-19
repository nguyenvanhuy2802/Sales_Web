package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Lấy session hiện tại mà không tạo mới
        if(session != null){
            session.invalidate(); // Hủy session
        }
        response.sendRedirect(request.getContextPath() + "/views/user/login.jsp");
    }
}
