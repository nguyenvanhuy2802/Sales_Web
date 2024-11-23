package org.example.servlet;

import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.PaymentDAO;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Payment;
import org.example.model.User;
import org.example.utils.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

        // Create and save the order
        Order order = new Order(user.getUserId(), totalAmount);
        int newOrderId = orderDAO.addOrderId(new Order(user.getUserId(), totalAmount));

        // Create and save the order item
        OrderItem orderItem = new OrderItem(newOrderId, productId, quantity, unitPrice);
        orderItemDAO.addOrderItem(orderItem);

        // Create and save the payment
        Payment payment = new Payment(newOrderId, totalAmount, paymentMethod);
        paymentDAO.addPayment(payment);

        // Prepare email content
        String subject = "Hóa đơn mua hàng từ [Tên Cửa Hàng]";
        String messageContent = generateInvoiceEmailContent(user, order, orderItem);

        try {
            // Send the invoice email
            EmailUtil.sendEmail(user.getEmail(), subject, messageContent);
        } catch (Exception e) {
            // Handle email sending failure (optional: log the error, notify admin, etc.)
            e.printStackTrace();
        }

        // Redirect to the orders page
        response.sendRedirect(request.getContextPath() + "/orders");
    }

    private String generateInvoiceEmailContent(User user,  OrderItem orderItem, BigDecimal unitPrice, BigDecimal totalAmount, String paymentMethod) {
        // You can enhance this method to include more details and format it as needed
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Hóa Đơn Mua Hàng</h2>");
        sb.append("<p>Xin chào ").append(user.getName()).append(",</p>");
        sb.append("<p>Cảm ơn bạn đã mua hàng tại [Tên Cửa Hàng]. Dưới đây là chi tiết đơn hàng của bạn:</p>");
        sb.append("<table border='1' cellpadding='10' cellspacing='0'>");
        sb.append("<tr>")
                .append("<th>Sản phẩm</th>")
                .append("<th>Số lượng</th>")
                .append("<th>Đơn giá</th>")
                .append("<th>Tổng cộng</th>")
                .append("</tr>");
        sb.append("<tr>")
                .append("<td>").append(orderItem.get).append("</td>")
                .append("<td>").append(orderItem.getQuantity()).append("</td>")
                .append("<td>").append(formatCurrency(unitPrice)).append("</td>")
                .append("<td>").append(formatCurrency(totalAmount)).append("</td>")
                .append("</tr>");
        sb.append("</table>");
        sb.append("<p>Tổng thanh toán: <strong>").append(formatCurrency(order.getTotalAmount())).append("</strong></p>");
        sb.append("<p>Phương thức thanh toán: ").append(paymentMethod).append("</p>");
        sb.append("<p>Địa chỉ giao hàng: ").append(user.getAddress()).append("</p>");
        sb.append("<p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.</p>");
        sb.append("<p>Trân trọng,<br/>[Tên Cửa Hàng]</p>");
        return sb.toString();
    }

    private String formatCurrency(BigDecimal amount) {
        // Format the currency as per your locale and requirements
        return String.format("VND %,d", amount.longValue());
    }
}
