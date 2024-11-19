package org.example.servlet;

import org.example.DAO.ProductDAO;
import org.example.model.Product;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/buyNow")
public class BuyNowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = 0;
        int quantity = 0;
        try {
            // Lấy thông tin sản phẩm từ yêu cầu
            productId = Integer.parseInt(request.getParameter("productId"));
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        // Lấy sản phẩm từ cơ sở dữ liệu
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            // Xử lý khi sản phẩm không tồn tại
            response.sendRedirect("product");
            return;
        }

        // Lấy người dùng từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            response.sendRedirect("login?redirect=buyNow");
            return;
        }

        // Đặt sản phẩm và số lượng vào request
        request.setAttribute("product", product);
        request.setAttribute("quantity", quantity);
//        System.out.println("Giá cua sản phẩm là: " + product.getPrice());
//        System.out.println("Số lượng là: " + quantity);

        // Chuyển hướng đến trang thanh toán
        request.getRequestDispatcher("/views/user/checkout.jsp").forward(request, response);
    }
}