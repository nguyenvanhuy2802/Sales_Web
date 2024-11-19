package org.example.servlet;

import org.example.DAO.CategoryDAO;
import org.example.DAO.ProductDAO;
import org.example.model.Category;
import org.example.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/productDetail")
public class ProductDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if(idParam != null){
            try {
                int id = Integer.parseInt(idParam);
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.getProductById(id);
                if(product != null){
                    request.setAttribute("product", product);
                    request.setAttribute("categoryId", product.getCategoryId());
                    CategoryDAO categoryDAO = new CategoryDAO();
                    Category category = categoryDAO.getCategoryById(product.getCategoryId());
                    request.setAttribute("categoryName", category.getName());
                    request.getRequestDispatcher("/views/user/product.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        // Nếu không tìm thấy sản phẩm hoặc id không hợp lệ, chuyển hướng về trang home
        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }
}
