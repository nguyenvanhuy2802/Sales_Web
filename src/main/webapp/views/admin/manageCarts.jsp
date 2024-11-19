<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-4">
    <h2>Quản Lý Giỏ Hàng</h2>
    <c:if test="${empty carts}">
        <div class="alert alert-info mt-4">Hiện không có giỏ hàng nào.</div>
    </c:if>
    <c:if test="${not empty carts}">
        <table class="table table-bordered table-hover mt-4">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Tên Người Dùng</th>
                    <th>Ngày Tạo</th>
                    <th>Số Mặt Hàng</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cart" items="${carts}" varStatus="status">
                    <tr>
                        <th>${status.count}</th>
                        <td>${cart.customerId}</td>
                        <td>${cart.createdAt}</td>
                        <td>${cart.items.size()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/viewCartDetails?cartId=${cart.cartId}" class="btn btn-info btn-sm">Chi Tiết</a>
                            <form action="${pageContext.request.contextPath}/admin/manageCarts" method="post" class="d-inline" onsubmit="return confirm('Bạn có chắc chắn muốn xóa giỏ hàng này?');">
                                <input type="hidden" name="action" value="clearCart">
                                <input type="hidden" name="cartId" value="${cart.cartId}">
                                <button type="submit" class="btn btn-danger btn-sm">Xóa Giỏ Hàng</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<%@ include file="adminFooter.jsp" %>
