<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2>Đăng Ký</h2>

<c:if test="${not empty message}">
    <div class="alert alert-danger">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">
    <div class="mb-3">
        <label for="username" class="form-label">Tên Đăng Nhập:</label>
        <input type="text" class="form-control" id="username" name="username" required>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Mật Khẩu:</label>
        <input type="password" class="form-control" id="password" name="password" required>
    </div>
    <div class="mb-3">
        <label for="name" class="form-label">Họ và tên:</label>
        <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input type="email" class="form-control" id="email" name="email" required>
    </div>
    <div class="mb-3">
        <label for="phone" class="form-label">Số Điện Thoại:</label>
        <input type="text" class="form-control" id="phone" name="phone">
    </div>
    <div class="mb-3">
        <label for="address" class="form-label">Địa Chỉ:</label>
        <input type="text" class="form-control" id="address" name="address">
    </div>
    <div class="mb-3">
        <label for="profile_image" class="form-label">Ảnh Đại Diện:</label>
        <input type="file" class="form-control" id="profile_image" name="profile_image">
    </div>
    <button type="submit" class="btn btn-success">Đăng Ký</button>
</form>

<%@ include file="footer.jsp" %>
