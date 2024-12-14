package org.example.servlet.admin;

import org.example.DAO.CategoryDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.ProductDAO;
import org.example.DAO.UserDAO;
import org.example.model.Category;
import org.example.model.Order;
import org.example.model.Product;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/homeAdmin")
public class HomeAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        OrderDAO orderDAO = new OrderDAO();

        List<Product> productList = productDAO.getAllProducts();
        List<Category> categoryList = categoryDAO.getAllCategories();
        List<User> userList = userDAO.getAllUsers();
        List<Order> orderList = orderDAO.getAllOrders();

        req.setAttribute("productList", productList);
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("userList", userList);
        req.setAttribute("orderList", orderList);

        req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
    }
}
