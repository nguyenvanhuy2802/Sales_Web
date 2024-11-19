<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2>Đăng Nhập</h2>

<c:if test="${not empty message}">
    <div class="alert alert-danger">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/login" method="post">
    <!-- Giữ lại tham số redirect và id -->
    <c:if test="${not empty redirect}">
        <input type="hidden" name="redirect" value="${redirect}">
    </c:if>
    <c:if test="${not empty id}">
        <input type="hidden" name="id" value="${id}">
    </c:if>

    <div class="mb-3">
        <label for="username" class="form-label">Tên Đăng Nhập:</label>
        <input type="text" class="form-control" id="username" name="username" required>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Mật Khẩu:</label>
        <input type="password" class="form-control" id="password" name="password" required>
    </div>
    <button type="submit" class="btn btn-primary">Đăng Nhập</button>
</form>

<%@ include file="footer.jsp" %>
