package org.example.servlet.admin;

import org.example.DAO.CategoryDAO;
import org.example.model.Category;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categoryManagement")
public class CategoryManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryDAO categoryDao = new CategoryDAO();
        List<Category> categoryList = categoryDao.getAllCategories();
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("views/admin/categoryManagement.jsp").forward(req, resp);
    }
}
