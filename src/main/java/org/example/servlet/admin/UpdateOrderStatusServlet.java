package org.example.servlet.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.OrderDAO;
import org.example.model.Order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cài đặt phản hồi kiểu JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu từ body yêu cầu
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestData = objectMapper.readValue(request.getReader(), Map.class);

        String orderId = requestData.get("orderId");
        String newStatus = requestData.get("status");

        Map<String, Object> responseData = new HashMap<>();
        PrintWriter out = response.getWriter();
        OrderDAO orderDAO = new OrderDAO();
        Order currentOrder = orderDAO.getOrderById(Integer.parseInt(orderId));
        currentOrder.setStatus(newStatus);
        try {
            boolean result = orderDAO.updateOrder(currentOrder);
            if (result) {
                responseData.put("success", true);
                responseData.put("message", "Updated successfully!");
            } else {
                responseData.put("success", false);
                responseData.put("message", "Order ID not found or no changes made.");
            }
        } catch (Exception e) {
            // Xử lý lỗi
            responseData.put("success", false);
            responseData.put("message", "Error updating order status: " + e.getMessage());
        }

        // Trả về phản hồi JSON
        objectMapper.writeValue(out, responseData);
    }
}
