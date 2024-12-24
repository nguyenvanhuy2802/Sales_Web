package org.example.servlet.security;

import com.google.gson.Gson;
import org.example.DAO.KeyDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.SignatureDAO;
import org.example.model.Order;
import org.example.model.PublicKey;
import org.example.model.Signature;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/saveSignature")
public class SaveSignatureServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String jsonData = jsonBuilder.toString();

        // Chuyển JSON thành đối tượng User
        Gson gson = new Gson();
        Signature signature = gson.fromJson(jsonData, Signature.class);

        // Lưu chữ ký vào database
        SignatureDAO signatureDAO = new SignatureDAO();
        if(signature.getSignTime() == null){
            signatureDAO.addSignature(signature.getSignValue(), signature.getOrderId());
            System.out.println("Lưu thành công");
        }
        else{
            signatureDAO.updateSignature(signature);
            System.out.println("Lưu thành công");
        }


        KeyDAO keyDAO = new KeyDAO();
        OrderDAO orderDAO = new OrderDAO();

        List<PublicKey> keyList =  keyDAO.getPublicKeyByUserIdAndStatus(signature.getUserId(), "Enabled");
        // Xử lý dữ liệu (ví dụ: lưu vào database)
        if(!keyList.isEmpty()){
            Order currentOrder = orderDAO.getOrderById(signature.getOrderId());
            currentOrder.setKeyId(keyList.get(0).getKeyId());
            orderDAO.updateOrder(currentOrder);
        }
        else{
            System.out.println("Không có giá trị keyList");
        }
        System.out.println("Value: " + signature.getSignValue() + ", order id: " + signature.getOrderId() + ", time: " + signature.getSignTime() + "userId: " + signature.getUserId() );

        // Phản hồi lại client
        response.setContentType("text/plain");
        response.getWriter().write("User data received successfully!");

    }
}
