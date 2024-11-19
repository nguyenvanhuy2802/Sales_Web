package org.example.servlet;

import com.google.gson.Gson;
import org.example.DAO.OrderItemDAO;
import org.example.model.OrderItem;
import org.example.model.OrderItemDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/order-items")
public class OrderItemServlet extends HttpServlet {
    private OrderItemDAO orderItemDAO;

    @Override
    public void init() throws ServletException {
        orderItemDAO = new OrderItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing orderId parameter");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemsWithProductByOrderId(orderId);

            // Convert to JSON
            Gson gson = new Gson();
            String json = gson.toJson(orderItems);

            // Set response type and write JSON
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid orderId parameter");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}

