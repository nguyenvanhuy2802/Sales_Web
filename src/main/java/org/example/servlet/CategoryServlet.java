package org.example.servlet;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();

        Category category = categoryDAO.getCategoryById(categoryId);
        if(category!= null){
            request.setAttribute("categoryName", category.getName());
        }
        List<Product> productList = productDAO.getAllProducts();
        List<Product> productListByCategoryId = new ArrayList<>();
        if(!productList.isEmpty()){
            for (Product product : productList){
                if(product.getCategoryId() == categoryId){
                    productListByCategoryId.add(product);
                }
            }
        }
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("productList", productListByCategoryId);
        request.getRequestDispatcher("views/user/category.jsp").forward(request, response);
    }
}
