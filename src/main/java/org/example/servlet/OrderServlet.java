package org.example.servlet;

import org.example.DAO.KeyDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.OrderItemDAO;
import org.example.DAO.SignatureDAO;
import org.example.model.*;
import org.example.security.ElectronicSignature;
import org.example.security.Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, RuntimeException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllOrders();

        List<Order> pendingOrders = new ArrayList<>();
        List<Order> completeOrders = new ArrayList<>();
        List<Order> cancelledOrders = new ArrayList<>();

        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                if (order.getCustomerId() == user.getUserId()) {
                    if (order.getStatus().equalsIgnoreCase("pending")) {
                        pendingOrders.add(order);
                    } else if (order.getStatus().equalsIgnoreCase("canceled")) {
                        cancelledOrders.add(order);
                    } else if (order.getStatus().equalsIgnoreCase("complete")) {
                        completeOrders.add(order);
                    }
                }
            }
        }

        req.setAttribute("pendingOrders", pendingOrders);
        req.setAttribute("completeOrders", completeOrders);
        req.setAttribute("cancelledOrders", cancelledOrders);


        req.getRequestDispatcher("/views/user/order.jsp").forward(req, resp);
    }

}
