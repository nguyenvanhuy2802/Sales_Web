package org.example.servlet;

import org.example.DAO.CartItemDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.PaymentDAO;
import org.example.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/checkout-lot")
public class CheckoutLotServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;
    private CartItemDAO cartItemDao;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
        paymentDAO = new PaymentDAO();
        cartItemDao = new CartItemDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productIds");
        String[] quantities = request.getParameterValues("quantities");
        String[] unitPrices = request.getParameterValues("unitPrices");
        String[] cartitemIds = request.getParameterValues("cartItemIds");
        String totalPrice = request.getParameter("grandTotal");
        String paymentMethod = request.getParameter("paymentMethod");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Thêm order mới và các orderItems
        int newOrderId = orderDAO.addOrderId(new Order(user.getUserId(), new BigDecimal(totalPrice != null ? totalPrice : "0")));
        for (int i = 0; i < productIds.length; i++) {
            orderItemDAO.addOrderItem(new OrderItem(newOrderId, Integer.parseInt(productIds[i]), Integer.parseInt(quantities[i]), new BigDecimal(unitPrices[i])));
        }
        // Thêm payment mới
        paymentDAO.addPayment(new Payment(newOrderId, new BigDecimal(totalPrice != null ? totalPrice : "0"), paymentMethod));

        // Xóa các cartItem trong giỏ hàng được checkbox
        for (String cartitemId : cartitemIds) {
            cartItemDao.deleteCartItem(Integer.parseInt(cartitemId));
        }
        response.sendRedirect(request.getContextPath() + "/orders");


    }
}
