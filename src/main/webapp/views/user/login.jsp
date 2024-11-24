<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="card-title text-center mb-4">Đăng Nhập</h3>

                    <!-- Thông báo lỗi -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Form Đăng Nhập -->
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
                            <input type="text" class="form-control" id="username" name="username" placeholder="Nhập tên đăng nhập" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật Khẩu:</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Đăng Nhập</button>
                        </div>
                    </form>

                    <div class="mt-3 text-center">
                        <span>Bạn chưa có tài khoản? </span>
                        <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">Đăng Ký</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
