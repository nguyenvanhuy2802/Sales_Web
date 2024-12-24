package org.example.servlet.security;

import org.example.model.User;
import org.example.utils.EmailUtil;
import org.example.utils.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sendConfirmationEmail")
public class SendConfirmationEmailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin người dùng
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String userEmail = user.getEmail();
        String confirmationLink =  "http://localhost:8080/webtest/confirmKeyCreation?token=" + TokenUtil.generateToken(userEmail);

        // Nội dung email
        String emailContent = "<p>Xin chào,</p>"
                + "<p>Bạn đã yêu cầu tạo key mới. Vui lòng xác nhận trong vòng 5 phút.</p>"
                + "<a href='" + confirmationLink + "'>Xác nhận</a>"
                + " hoặc "
                + "<a href='" + "http://localhost:8080/webtest/rejectKeyCreation?token=" + TokenUtil.generateToken(userEmail) + "'>Từ chối</a>";

        // Gửi email
        try {
            EmailUtil.sendEmail(userEmail, "Xác nhận tạo key", emailContent);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
