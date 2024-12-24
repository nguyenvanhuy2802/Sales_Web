package org.example.servlet.security;

import com.google.gson.Gson;
import org.example.DAO.KeyDAO;
import org.example.DAO.OrderDAO;
import org.example.model.PublicKey;
import org.example.model.User;
import org.example.model.Order;
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
import java.util.Base64;
import java.util.List;

import java.time.Instant;
import java.time.format.DateTimeParseException;

@WebServlet("/reportKey")
public class ReportKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Parse the request body (JSON)
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                json.append(line);
            }

            // Deserialize the JSON into an object containing the exposureTimestamp
            Gson gson = new Gson();
            ReportRequest reportRequest = gson.fromJson(json.toString(), ReportRequest.class);

            String exposureTimestampStr = reportRequest.getExposureTimestamp();

            // Check if exposureTimestamp is null or empty
            if (exposureTimestampStr == null || exposureTimestampStr.trim().isEmpty()) {
                throw new IllegalArgumentException("exposureTimestamp is missing or empty.");
            }

            // Try to parse the exposureTimestamp in ISO 8601 format
            Timestamp exposureTimestamp;
            try {
                Instant instant = Instant.parse(exposureTimestampStr);  // Parse ISO 8601 format
                exposureTimestamp = Timestamp.from(instant);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid exposureTimestamp format. Expected ISO 8601 format.");
            }

            // Proceed with the logic...
            KeyDAO keyDAO = new KeyDAO();
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            if (user == null) {
                throw new IllegalStateException("User session not found.");
            }

            List<PublicKey> publicKeyList = keyDAO.getPublicKeyByUserIdAndStatus(user.getUserId(), "Enabled");
            if (!publicKeyList.isEmpty()) {
                PublicKey currentPublicKey = publicKeyList.get(0);
                currentPublicKey.setLeakTime(exposureTimestamp);
                currentPublicKey.setReportTime(Timestamp.valueOf(LocalDateTime.now()));
                keyDAO.updateKey(currentPublicKey, "Disabled");
            }

            // Xóa các đơn hàng trong khoảng thời gian bị lo va report
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orderList = orderDAO.getAllOrders();
            if(!orderList.isEmpty()){
                for(Order currentOrder: orderList){
                    if (currentOrder.getOrderDate().after(exposureTimestamp) && currentOrder.getOrderDate().before(Timestamp.valueOf(LocalDateTime.now())) && currentOrder.getStatus().equalsIgnoreCase("pending") && currentOrder.getCustomerId() == user.getUserId()){
                      currentOrder.setStatus("canceled");
                      orderDAO.updateOrder(currentOrder);
                    }
                }
            }



            // Generate new keys (public/private key pair)
            ElectronicSignature.genDSAKey();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(ElectronicSignature.getPublicKeyDSA().getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(ElectronicSignature.getPrivateKeyDSA().getEncoded());

            // Save new key into the database
            PublicKey publicKey = new PublicKey(user.getUserId(), publicKeyBase64);
            boolean result = keyDAO.addKey(publicKey);

            // Return the public and private keys in JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"publicKey\":\"" + publicKeyBase64 + "\", \"privateKey\":\"" + privateKeyBase64 + "\"}");
            System.out.println("Chạy tới đây!!!");
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();

            // Send error response with a detailed message
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the report: " + e.getMessage());
        }
    }

    // Inner class to map the JSON body (for exposureTimestamp)
    private class ReportRequest {
        private String exposureTimestamp;

        public String getExposureTimestamp() {
            return exposureTimestamp;
        }

        public void setExposureTimestamp(String exposureTimestamp) {
            this.exposureTimestamp = exposureTimestamp;
        }
    }
}



