package org.example.servlet;

import org.example.DAO.OrderDAO;
import org.example.model.Order;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllOrders();

        List<Order> pendingOrders = new ArrayList<>();
        List<Order> cancelledOrders = new ArrayList<>();

        if(!orderList.isEmpty()){
            for (Order order: orderList){
                if(order.getCustomerId() == user.getUserId()){
                    if(order.getStatus().equalsIgnoreCase("pending")){
                        pendingOrders.add(order);
                    }
                    else if(order.getStatus().equalsIgnoreCase("cancelled")){
                        cancelledOrders.add(order);
                    }
                }
            }
        }

        req.setAttribute("pendingOrders", pendingOrders);
        req.setAttribute("cancelledOrders", cancelledOrders);


        req.getRequestDispatcher("/views/user/order.jsp").forward(req, resp);
    }
}
