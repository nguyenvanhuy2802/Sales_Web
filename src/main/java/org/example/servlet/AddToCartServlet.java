package org.example.servlet;


import org.example.DAO.CartDAO;
import org.example.DAO.CartItemDAO;
import org.example.model.Cart;
import org.example.model.CartItem;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");
        int productId = Integer.parseInt(productIdParam);
        int quantity = Integer.parseInt(quantityParam);


        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        CartDAO cartDAO = new CartDAO();
        Cart currentCart = null;
        CartItemDAO cartItemDAO = new CartItemDAO();
        List<Cart> cartList = cartDAO.getAllCarts();
        if (!cartList.isEmpty()) {
            for (Cart cart : cartList) {
                if (cart.getCustomerId() == user.getUserId()) {
                    currentCart = cart;
                    break;
                }
            }
        }
        if (currentCart != null) {
            List<CartItem> cartItemList = cartItemDAO.getAllCartItems();
                CartItem currentCartItem = null;
                for (CartItem cartItem : cartItemList) {
                    if (cartItem.getProductId() == productId && cartItem.getCartId() == currentCart.getCartId()) {
                        currentCartItem = cartItem;
                        break;
                    }
                }
                if (currentCartItem != null) {
                    currentCartItem.setQuantity(currentCartItem.getQuantity() + quantity);
                    cartItemDAO.updateCartItem(currentCartItem);
                }
                else{
                    cartItemDAO.addCartItem(new CartItem(currentCart.getCartId(), productId, quantity));
                }

        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
