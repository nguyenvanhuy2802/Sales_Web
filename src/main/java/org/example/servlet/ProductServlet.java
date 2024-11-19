package org.example.servlet;

import org.example.DAO.CategoryDAO;
import org.example.DAO.ProductDAO;
import org.example.model.Category;
import org.example.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getAllProducts();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.getAllCategories();
        req.setAttribute("productList", productList);
        req.setAttribute("categoryList", categoryList);

        req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
    }
}
