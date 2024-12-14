package org.example.servlet;

import com.google.gson.Gson;
import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.model.Order;
import org.example.model.OrderItemDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cancel-order")
public class CancelOrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderDAO.getOrderById(orderId);
                boolean success = false;
                if (order != null) {
                    order.setStatus("canceled");
                    success = orderDAO.updateOrder(order); // Ensure that the order is updated in the database
                }
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String jsonResponse;

                if (success) {
                    // Return the details of the cancelled order
                    jsonResponse = new Gson().toJson(new ResponseMessage("success", "Đơn hàng đã được hủy thành công.", order));
                } else {
                    jsonResponse = new Gson().toJson(new ResponseMessage("error", "Không tìm thấy đơn hàng hoặc không thể hủy.", null));
                }
                resp.getWriter().write(jsonResponse);
            } catch (NumberFormatException e) {
                resp.getWriter().write(new Gson().toJson(new ResponseMessage("error", "ID đơn hàng không hợp lệ.", null)));
            }
        } else {
            resp.getWriter().write(new Gson().toJson(new ResponseMessage("error", "ID đơn hàng không được trống.", null)));
        }
    }

    static class ResponseMessage {
        private final String status;
        private final String message;
        private final Order order;

        public ResponseMessage(String status, String message, Order order) {
            this.status = status;
            this.message = message;
            this.order = order;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public Order getOrder() {
            return order;
        }
    }
}

