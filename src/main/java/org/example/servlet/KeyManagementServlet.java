package org.example.servlet;

import org.example.DAO.KeyDAO;
import org.example.model.PublicKey;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/keyManagement")
public class KeyManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        KeyDAO keyDAO = new KeyDAO();
        List<PublicKey> publicKeyList = keyDAO.getPublicKeyByUserIdAndStatus(user.getUserId(), "Enabled");
        if(!publicKeyList.isEmpty()){
          req.setAttribute("publicKey", publicKeyList.get(0));
        }
        req.getRequestDispatcher("/views/user/keyManagement.jsp").forward(req, resp);
    }
}
