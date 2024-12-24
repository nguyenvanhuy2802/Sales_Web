package org.example.servlet.security;

import com.google.gson.Gson;
import org.example.DAO.KeyDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.SignatureDAO;
import org.example.model.Order;
import org.example.model.PublicKey;
import org.example.model.Signature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getOrdersAndSignatures")
public class GetOrdersAndSignaturesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ DAOO
            OrderDAO orderDAO = new OrderDAO();
            SignatureDAO signatureDAO = new SignatureDAO();
            KeyDAO keyDAO = new KeyDAO();
            List<Order> orderList = orderDAO.getAllOrders();
            List<Signature> signatureList = signatureDAO.getAllSignatures();
            List<PublicKey> keyList = keyDAO.getAllKeys();

            // Kiểm tra null
            if (orderList == null) orderList = List.of();
            if (signatureList == null) signatureList = List.of();
            if (keyList == null) keyList = List.of();

            // Đóng gói dữ liệu
            DataResponse dataResponse = new DataResponse(orderList, signatureList, keyList);

            // Chuyển thành JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(dataResponse);

            // Gửi JSON về client
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            // Ghi lỗi ra log server (nếu cần)
            e.printStackTrace();

            // Gửi mã lỗi và thông báo về client
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\":\"Failed to retrieve data.\"}");
        }
    }

    // Lớp chứa dữ liệu để gửi
    public static class DataResponse {
        private List<Order> orderList;
        private List<Signature> signatureList;
        private List<PublicKey> keyList;

        public DataResponse(List<Order> orderList, List<Signature> signatureList, List<PublicKey> keyList) {
            this.orderList = orderList;
            this.signatureList = signatureList;
            this.keyList = keyList;
        }

        public List<PublicKey> getKeyList() {
            return keyList;
        }

        public List<Order> getOrderList() {
            return orderList;
        }

        public List<Signature> getSignatureList() {
            return signatureList;
        }
    }
}
