<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-7">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="card-title text-center mb-4">Đăng Ký</h3>

                    <!-- Thông báo lỗi -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Form Đăng Ký -->
                    <form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="username" class="form-label">Tên Đăng Nhập:</label>
                            <input type="text" class="form-control" id="username" name="username" placeholder="Nhập tên đăng nhập" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật Khẩu:</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">Họ và Tên:</label>
                            <input type="text" class="form-control" id="name" name="name" placeholder="Nhập họ và tên" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Nhập email" required>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Số Điện Thoại:</label>
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại">
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Địa Chỉ:</label>
                            <input type="text" class="form-control" id="address" name="address" placeholder="Nhập địa chỉ">
                        </div>
                        <div class="mb-3">
                            <label for="profile_image" class="form-label">Ảnh Đại Diện:</label>
                            <input type="file" class="form-control" id="profile_image" name="profile_image" accept="image/*">
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-success">Đăng Ký</button>
                        </div>
                    </form>

                    <div class="mt-3 text-center">
                        <span>Bạn đã có tài khoản? </span>
                        <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Đăng Nhập</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
