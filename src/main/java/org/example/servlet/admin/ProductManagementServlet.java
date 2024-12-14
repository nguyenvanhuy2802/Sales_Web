package org.example.servlet.admin;

import org.example.DAO.CategoryDAO;
import org.example.DAO.ProductDAO;
import org.example.model.Category;
import org.example.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/productManagement")
public class ProductManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getAllProducts();
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("views/admin/productManagement.jsp").forward(req, resp);
    }
}
