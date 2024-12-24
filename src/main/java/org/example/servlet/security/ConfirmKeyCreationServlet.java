package org.example.servlet.security;

import org.example.security.ElectronicSignature;
import org.example.utils.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

@WebServlet("/confirmKeyCreation")
public class ConfirmKeyCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        // Xác thực token (giới hạn thời gian 5 phút)
        if (TokenUtil.isValidToken(token)) {
            response.sendRedirect(request.getContextPath() + "/generateKey");
        } else {
            response.getWriter().write("Yêu cầu đã hết hạn hoặc không hợp lệ!");
        }
    }
}
