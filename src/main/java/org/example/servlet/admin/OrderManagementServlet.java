package org.example.servlet.admin;

import org.example.DAO.OrderDAO;
import org.example.DAO.ProductDAO;
import org.example.model.Order;
import org.example.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/orderManagement")
public class OrderManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllOrders();
        req.setAttribute("orderList", orderList);
        req.getRequestDispatcher("views/admin/orderManagement.jsp").forward(req, resp);
    }
}
