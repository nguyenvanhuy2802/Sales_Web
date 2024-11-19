package org.example.servlet;

import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.PaymentDAO;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Payment;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paymentMethod = request.getParameter("paymentMethod");
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        BigDecimal unitPrice = new BigDecimal(request.getParameter("unitPrice"));
        BigDecimal totalAmount = new BigDecimal(request.getParameter("totalAmount"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        int newOrderId = orderDAO.addOrderId(new Order(user.getUserId(), totalAmount));

        orderItemDAO.addOrderItem(new OrderItem(newOrderId, productId, quantity, unitPrice));
        paymentDAO.addPayment(new Payment(newOrderId, totalAmount, paymentMethod));
        response.sendRedirect(request.getContextPath() + "/orders");
    }
}
