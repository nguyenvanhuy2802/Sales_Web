<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="content-wrapper">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="card shadow-lg border-0">
                    <div class="card-body">
                        <!-- Tiêu đề nổi bật -->
                        <h3 class="card-title text-center mb-4 text-light bg-primary py-3 rounded-3 shadow-sm">
                            <i class="bi bi-person-circle me-2"></i>Đăng Nhập Quản Trị Viên
                        </h3>

                        <!-- Thông báo lỗi -->
                        <c:if test="${not empty message}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${message}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>

                        <!-- Form Đăng Nhập -->
                        <form action="${pageContext.request.contextPath}/loginAdmin" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label fw-bold">Tên Đăng Nhập:</label>
                                <input type="text"
                                       class="form-control border-primary shadow-sm"
                                       id="username"
                                       name="username"
                                       placeholder="Nhập tên đăng nhập"
                                       required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label fw-bold">Mật Khẩu:</label>
                                <input type="password"
                                       class="form-control border-primary shadow-sm"
                                       id="password"
                                       name="password"
                                       placeholder="Nhập mật khẩu"
                                       required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg shadow-sm">
                                     Đăng Nhập
                                </button>
                            </div>
                        </form>

                        <!-- Đăng ký -->
                        <div class="mt-4 text-center">
                            <span class="text-muted">Bạn chưa có tài khoản? </span>
                            <a href="${pageContext.request.contextPath}/register" class="text-primary fw-bold text-decoration-none">
                                Đăng Ký
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="adminFooter.jsp" %>
