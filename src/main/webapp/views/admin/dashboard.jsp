<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-4">
    <h2>Admin Dashboard</h2>

    <!-- Admin Menu Section -->
    <div class="row mt-5">
        <div class="col-md-6">
            <div class="card text-center">
                <div class="card-body">
                    <h3 class="card-title">Quản Lý Người Dùng</h3>
                    <p class="card-text">Xem và quản lý thông tin người dùng trong hệ thống.</p>
                    <a href="${pageContext.request.contextPath}/admin/manageUsers" class="btn btn-primary">
                        <i class="bi bi-person-fill"></i> Quản Lý Người Dùng
                    </a>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card text-center">
                <div class="card-body">
                    <h3 class="card-title">Quản Lý Giỏ Hàng</h3>
                    <p class="card-text">Xem và quản lý giỏ hàng của người dùng.</p>
                    <a href="${pageContext.request.contextPath}/admin/manageCarts" class="btn btn-primary">
                        <i class="bi bi-cart-fill"></i> Quản Lý Giỏ Hàng
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="adminFooter.jsp" %>
