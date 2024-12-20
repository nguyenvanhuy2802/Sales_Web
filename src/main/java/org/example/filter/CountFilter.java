package org.example.filter;


import org.example.DAO.CartDAO;
import org.example.DAO.CartItemDAO;
import org.example.DAO.OrderDAO;
import org.example.model.CartItem;
import org.example.model.Order;
import org.example.model.User;
import org.example.model.Cart;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*") // Áp dụng cho tất cả các URL
public class CountFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Chuyển đổi thành HttpServletRequest để truy cập session
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0); // Đặt ngày hết hạn của cache.

        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                CartDAO cartDAO = new CartDAO();

                List<Cart> cartList = cartDAO.getAllCarts();
                Cart currentCart = null;
                for (Cart cart : cartList) {
                    if (cart.getCustomerId() == user.getUserId()) {
                        currentCart = cart;
                        break;
                    }
                }

                if (currentCart != null) {
                    CartItemDAO cartItemDAO = new CartItemDAO();
                    List<CartItem> cartItemList = cartItemDAO.getAllCartItems();
                    int cartItemCount = 0;
                    if (!cartItemList.isEmpty()) {
                        for (CartItem cartItem : cartItemList) {
                            if (cartItem.getCartId() == currentCart.getCartId()) {
                                cartItemCount += 1;
                            }
                        }
                    }

                    session.setAttribute("cartItemCount", cartItemCount);
                } else {
                    session.setAttribute("cartItemCount", 0);
                }

                OrderDAO orderDAO = new OrderDAO();
                List<Order> orderList = orderDAO.getAllOrders();
                if (!orderList.isEmpty()) {
                    int orderCount = 0;
                    for (Order order : orderList) {
                        if (order.getCustomerId() == user.getUserId() && order.getStatus().equalsIgnoreCase("pending")) {
                            orderCount += 1;
                        }
                    }

                    session.setAttribute("orderCount", orderCount);
                    session.setAttribute("notificationCount", orderCount);

                } else {
                    session.setAttribute("orderCount", 0);
                    session.setAttribute("notificationCount", 0);
                }

            } else {
                session.setAttribute("cartItemCount", 0);
                session.setAttribute("orderCount", 0);
                session.setAttribute("notificationCount", 0);

            }
        }

        // Tiếp tục xử lý yêu cầu
        chain.doFilter(request, response);
    }
}
