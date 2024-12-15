package org.example.servlet;

import org.example.DAO.KeyDAO;
import org.example.model.PublicKey;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/uploadKey")
public class UploadKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Đọc dữ liệu publicKey từ yêu cầu trực tiếp
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            String publicKey = requestBody.toString().replace("{\"publicKey\":\"", "").replace("\"}", "");

            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            if (user == null || publicKey == null || publicKey.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            KeyDAO keyDAO = new KeyDAO();
            // Disable old keys
            List<PublicKey> publicKeys = keyDAO.getPublicKeyByUserIdAndStatus(user.getUserId(), "Enabled");
            for (PublicKey oldKey : publicKeys) {
                oldKey.setStatus("Disabled");
                keyDAO.updateKey(oldKey);
            }

            // Add new key
            PublicKey newKey = new PublicKey(user.getUserId(), publicKey);
            newKey.setStatus("Enabled");
            keyDAO.addKey(newKey);

            // Trả về public key mới
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"publicKey\":\"" + publicKey + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error uploading key");
        }
    }
}