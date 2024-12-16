package org.example.servlet;

import org.example.DAO.KeyDAO;
import org.example.model.PublicKey;
import org.example.model.User;
import org.example.security.ElectronicSignature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@WebServlet("/reportKey")
public class ReportKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        try {
            KeyDAO keyDAO = new KeyDAO();
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            List<PublicKey> publicKeyList = keyDAO.getPublicKeyByUserIdAndStatus(user.getUserId(), "Enabled");
            if(!publicKeyList.isEmpty()) {
                PublicKey currentPublicKey = publicKeyList.get(0);
                currentPublicKey.setReportTime(Timestamp.valueOf(LocalDateTime.now()));
                keyDAO.updateKey(currentPublicKey, "Disabled");
            }

            ElectronicSignature.genDSAKey();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(ElectronicSignature.getPublicKeyDSA().getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(ElectronicSignature.getPrivateKeyDSA().getEncoded());

            // Lưu key vào database
            PublicKey publicKey = new PublicKey(user.getUserId(), publicKeyBase64);
            boolean result = keyDAO.addKey(publicKey);

            // Trả về public key dưới dạng JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"publicKey\":\"" + publicKeyBase64 + "\", \"privateKey\":\"" + privateKeyBase64 + "\"}");

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating key: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
