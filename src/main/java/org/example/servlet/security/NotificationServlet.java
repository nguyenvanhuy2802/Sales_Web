package org.example.servlet.security;

import com.google.gson.Gson;
import org.example.DAO.OrderDAO;
import org.example.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();

        // Lấy danh sách các đơn hàng
        List<Order> orders = orderDAO.getOrdersByStatus("pending");

        // Đặt kiểu trả về là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Sử dụng Gson để chuyển đổi danh sách đơn hàng thành JSON
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        response.getWriter().write(json);
    }
}
