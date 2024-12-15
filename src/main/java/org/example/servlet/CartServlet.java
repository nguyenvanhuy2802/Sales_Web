package org.example.servlet;

import org.example.DAO.CartDAO;
import org.example.DAO.CartItemDAO;
import org.example.DAO.ProductDAO;
import org.example.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        CartDAO cartDAO = new CartDAO();
        Cart currentCart = null;
        List<Cart> cartList = cartDAO.getAllCarts();
        for (Cart cart : cartList) {
            if (cart.getCustomerId() == user.getUserId()) {
                currentCart = cart;
                break;
            }
        }
        CartItemDAO cartItemDAO = new CartItemDAO();
        List<CartItem> cartItemListByCartId = new ArrayList<>();
        List<CartItem> cartItemList = cartItemDAO.getAllCartItems();
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getCartId() == currentCart.getCartId()) {
                cartItemListByCartId.add(cartItem);
            }
        }


        ProductDAO productDAO = new ProductDAO();
        List<CartItemWithProduct> cartItemWithProductList = new ArrayList<>();
        // Tính tổng giá trị giỏ hàng
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (CartItem item : cartItemListByCartId) {
            Product product = productDAO.getProductById(item.getProductId());
            if (product != null) { // Đảm bảo sản phẩm tồn tại
                cartItemWithProductList.add(new CartItemWithProduct(item, product));
                cartTotal = cartTotal.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }

        }

        req.setAttribute("cartItemList", cartItemWithProductList);
        req.setAttribute("cartTotal", cartTotal);
        req.getRequestDispatcher("/views/user/cart.jsp").forward(req, resp);
    }
}

