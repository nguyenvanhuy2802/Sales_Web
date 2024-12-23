package org.example.servlet.security;

import com.google.gson.Gson;
import org.example.DAO.KeyDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.SignatureDAO;
import org.example.model.*;
import org.example.security.ElectronicSignature;
import org.example.security.Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllOrders();

        List<Order> pendingOrders = new ArrayList<>();
        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                if (order.getCustomerId() == user.getUserId()) {
                    if (order.getStatus().equalsIgnoreCase("pending")) {
                        pendingOrders.add(order);

                    }
                }
            }

            Map<Integer, Boolean> orderResults = new HashMap<>();
            if (!pendingOrders.isEmpty()) {
                SignatureDAO signatureDAO = new SignatureDAO();
                for (Order currentOrder : pendingOrders) {
                    List<Signature> signatures = signatureDAO.getSignaturesByOrderId(currentOrder.getOrderId());
                    if (signatures.isEmpty()) {
//                    currentOrder.setIsVerified(0);
//                    orderDAO.updateOrder(currentOrder);
                        System.out.println("Chưa cos chữ ký");
                    } else {
                        String orderInfor = "";
                        OrderItemDAO orderItemDAO = new OrderItemDAO();
                        List<OrderItemDTO> orderItemList = orderItemDAO.getOrderItemsWithProductByOrderId(currentOrder.getOrderId());
                        if (!orderItemList.isEmpty()) {
                            int size = orderItemList.size();
                            String[] productIds = new String[size];
                            String[] productNames = new String[size];
                            String[] quantities = new String[size];
                            String[] unitPrices = new String[size];
                            for (int i = 0; i < size; i++) {
                                productIds[i] = String.valueOf(orderItemList.get(i).getOrderItemId());
                                productNames[i] = orderItemList.get(i).getProductName();
                                quantities[i] = String.valueOf(orderItemList.get(i).getQuantity());
                                unitPrices[i] = String.valueOf(orderItemList.get(i).getPrice());
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedTime = sdf.format(currentOrder.getOrderDate());
                            orderInfor = getOrderInformation(currentOrder.getOrderId(), currentOrder.getBuyerName(), currentOrder.getDeliveryAddress(), formattedTime, currentOrder.getTotalAmount(), productIds, productNames, quantities, unitPrices);
//                        System.out.println("Thong tin don hang la: " + orderInfor);
                        }

                        String hashCode = "";
                        try {
                            hashCode = Hash.hash(orderInfor);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
//                    System.out.println("Gia tri hash la: " + hashCode);
                        Signature signature = signatures.get(0);
                        KeyDAO publiKeyDA0 = new KeyDAO();
                        PublicKey currentPublicKey = publiKeyDA0.getPublicKeyByKeyId(currentOrder.getKeyId());
                        if (currentPublicKey != null) {
                            boolean result = false;
                            try {
                                result = ElectronicSignature.verify(hashCode, signature.getSignValue(), currentPublicKey.getName());
                            } catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException |
                                     NoSuchProviderException | InvalidKeySpecException e) {
                                throw new RuntimeException(e);
                            }

                            orderResults.put(currentOrder.getOrderId(), result);

                        } else {
                            System.out.println("Bạn chưa tạo khóa!");
                        }


                    }
                }

            }
            // Đặt kiểu trả về là JSON
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            // Sử dụng Gson để chuyển đổi danh sách đơn hàng thành JSON
            Gson gson = new Gson();
            String json = gson.toJson(orderResults);
            resp.getWriter().write(json);
        }
    }

    private String getOrderInformation(int orderId, String buyerName, String address, String orderDate, BigDecimal
            totalPrice, String[] productIds, String[] productNames, String[] quantities, String[] unitPrices) {
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
