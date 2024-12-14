package org.example.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

// Áp dụng filter cho các URL cần bảo vệ
@WebFilter(urlPatterns = {"/buyNow", "/addToCart", "/cart", "/orders"})
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Chuyển đổi thành HttpServletRequest và HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // Lấy session mà không tạo mới nếu chưa tồn tại
        HttpSession session = httpRequest.getSession(false);

        // Kiểm tra người dùng đã đăng nhập chưa
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (loggedIn) {
            // Tiếp tục xử lý yêu cầu
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
