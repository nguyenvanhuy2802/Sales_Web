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
import java.util.Base64;
import java.util.List;

@WebServlet("/generateKey")
public class GenerateKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ElectronicSignature.genDSAKey();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(ElectronicSignature.getPublicKeyDSA().getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(ElectronicSignature.getPrivateKeyDSA().getEncoded());

            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            // Vô hiệu hóa key hiện tại
            KeyDAO keyDAO = new KeyDAO();
            List<PublicKey> publicKeyList = keyDAO.getPublicKeyByUserIdAndStatus(user.getUserId(), "Enabled");
            if(!publicKeyList.isEmpty()){
                PublicKey currentPublicKey = publicKeyList.get(0);
                    keyDAO.updateKey(currentPublicKey, "Disabled");

            }
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
