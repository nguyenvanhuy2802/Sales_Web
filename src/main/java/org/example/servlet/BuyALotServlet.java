package org.example.servlet;

import org.example.DAO.CartItemDAO;
import org.example.DAO.ProductDAO;
import org.example.model.CartItem;
import org.example.model.CartItemWithProduct;
import org.example.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/buyALot")
public class BuyALotServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách productId được chọn từ form
        String[] selectedCartItemIds = request.getParameterValues("selectedCartItemIds");

        // Tạo danh sách các sản phẩm được chọn
        List<CartItemWithProduct> selectedItems = new ArrayList<>();
        CartItemDAO cartItemDao = new CartItemDAO();
        ProductDAO productDAO = new ProductDAO();
        if (selectedCartItemIds.length > 0) {
            for (String cartItemIds : selectedCartItemIds) {
                CartItem currentCartItem = cartItemDao.getCartItemById(Integer.parseInt(cartItemIds));
                Product currentProduct = productDAO.getProductById(currentCartItem.getProductId());
                selectedItems.add(new CartItemWithProduct(currentCartItem, currentProduct));
            }
        } else {
            System.out.println("Không có danh sách cartItem nào!!");
        }

        // Đặt danh sách các sản phẩm được chọn vào request để chuyển đến checkout.jsp
        request.setAttribute("selectedItems", selectedItems);

        // Chuyển hướng đến trang thanh toán
        request.getRequestDispatcher("/views/user/checkout-lot.jsp").forward(request, response);
    }
}
