package org.example.servlet;

import org.example.DAO.CartItemDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.PaymentDAO;
import org.example.model.*;
import org.example.security.Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

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
        String[] productNames = request.getParameterValues("productNames");
        String[] unitPrices = request.getParameterValues("unitPrices");
        String[] cartitemIds = request.getParameterValues("cartItemIds");
        BigDecimal totalPrice = new BigDecimal(request.getParameter("grandTotal"));
        String address = request.getParameter("address");
        String fullName = request.getParameter("fullName");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        Timestamp buyTime = Timestamp.valueOf(LocalDateTime.now());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = sdf.format(buyTime);

        //Lấy thông tin đơn hàng
        int orderId = orderDAO.getCurrentAutoIncrementOrderId();
        int currentOrderItemId = orderItemDAO.getCurrentAutoIncrementOrderItemId();
        String[] orderItemIds = new String[productIds.length];
        for (int i = 0; i < productIds.length ; i++) {
            orderItemIds[i] = String.valueOf(currentOrderItemId + i);
        }
        String orderInfor = getOrderInformation(orderId, fullName, address, formattedTime, totalPrice, orderItemIds, productNames, quantities, unitPrices );
        System.out.println("Thong tin don hang la(check-lot-out): " +orderInfor);
        String hashCode = "";
        try {
             hashCode = Hash.hash(orderInfor);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Gia tri hash la(check-lot-out): " + hashCode);
        // Thêm order mới và các orderItems
        int newOrderId = orderDAO.addOrderId(new Order(user.getUserId(),fullName,  totalPrice, address, hashCode));
        for (int i = 0; i < productIds.length; i++) {
            orderItemDAO.addOrderItem(new OrderItem(newOrderId, Integer.parseInt(productIds[i]), Integer.parseInt(quantities[i]), new BigDecimal(unitPrices[i])));
        }

        // Xóa các cartItem trong giỏ hàng được checkbox
        for (String cartitemId : cartitemIds) {
            cartItemDao.deleteCartItem(Integer.parseInt(cartitemId));
        }
        response.sendRedirect(request.getContextPath() + "/orders");

    }

    private String getOrderInformation(int orderId, String buyerName, String address, String orderDate, BigDecimal totalPrice, String[] productIds, String[] productNames, String[] quantities, String[] unitPrices){
        StringBuilder builder = new StringBuilder();
        builder.append(orderId).append(", ")
                .append(buyerName).append(", ")
                .append(address).append(", ")
                .append(orderDate).append(", ")
                .append(totalPrice).append(", ")
                .append("danh sách sản phẩm(");

        for (int i = 0; i < productIds.length; i++) {
            builder.append(productIds[i]).append(", ") // Product ID
                    .append(productNames[i]).append(", ") // Product Name
                    .append(quantities[i]).append(", ") // Quantity
                    .append(unitPrices[i]); // Unit Price

            if (i < productIds.length - 1) {
                builder.append("; ");
            }
        }
        builder.append(")");

        return builder.toString();
    }
}
