package org.example.servlet;


import org.example.DAO.CartDAO;
import org.example.DAO.CartItemDAO;
import org.example.model.Cart;
import org.example.model.CartItem;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("productId");
        int productId = Integer.parseInt(productIdParam);


        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        CartDAO cartDao = new CartDAO();
        List<Cart> cartList = cartDao.getAllCarts();
        Cart currentCart = null;
        if (!cartList.isEmpty()) {
            for (Cart cart : cartList) {
                if (cart.getCustomerId() == user.getUserId()) {
                    currentCart = cart;
                    break;
                }
            }
        }
        CartItemDAO cartItemDAO = new CartItemDAO();
        List<CartItem> cartItemList = cartItemDAO.getAllCartItems();
        CartItem currentCartItem = null;
        if (!cartItemList.isEmpty()) {
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getProductId() == productId) {
                    currentCartItem = cartItem;
                    break;
                }
            }
        }
        if(currentCartItem != null){
            cartItemDAO.deleteCartItem(currentCartItem.getCartItemId());
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
