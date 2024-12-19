package org.example.servlet;

import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.PaymentDAO;
import org.example.DAO.ProductDAO;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Product;
import org.example.model.User;
import org.example.security.Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        BigDecimal unitPrice = new BigDecimal(request.getParameter("unitPrice"));
        BigDecimal totalAmount = new BigDecimal(request.getParameter("totalAmount"));
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
        String orderInfor = getOrderInformation(orderId, fullName, address, formattedTime, totalAmount, String.valueOf(productId), productName, String.valueOf(quantity), String.valueOf(unitPrice));
        System.out.println("Thong tin don hang la: " + orderInfor);
        String hashCode = "";
        try {
            hashCode = Hash.hash(orderInfor);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Gia tri hash la: " + hashCode);
        // Thêm order mới và các orderItems
        int newOrderId = orderDAO.addOrderId(new Order(user.getUserId(), fullName, totalAmount, address, hashCode));

        // Create and save the order item
        OrderItem orderItem = new OrderItem(newOrderId, productId, quantity, unitPrice);
        orderItemDAO.addOrderItem(orderItem);

        // Redirect to the orders page
        response.sendRedirect(request.getContextPath() + "/orders");
    }

    private String getOrderInformation(int orderId, String buyerName, String address, String orderDate, BigDecimal totalPrice, String productId, String productName, String quantity, String unitPrice) {
        StringBuilder builder = new StringBuilder();
        builder.append(orderId).append(", ")
                .append(buyerName).append(", ")
                .append(address).append(", ")
                .append(orderDate).append(", ")
                .append(totalPrice).append(", ")
                .append("danh sách sản phẩm(");

        builder.append(productId).append(", ") // Product ID
                .append(productName).append(", ") // Product Name
                .append(quantity).append(", ") // Quantity
                .append(unitPrice); // Unit Price
        builder.append(")");

        return builder.toString();
    }


}









