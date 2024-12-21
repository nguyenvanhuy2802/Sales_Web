<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="context-path" content="${pageContext.request.contextPath}">
    <title>Web Bán Linh Kiện Máy Tính</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Favicon -->
    <link rel="icon" href="${pageContext.request.contextPath}/images/computer.png" type="image/png">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerstyles.css">

</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
        <div class="container-fluid">
            <!-- Logo và tên thương hiệu -->
          <a class="navbar-brand fw-bold d-flex align-items-center" href="${pageContext.request.contextPath}/product">
              <img src="${pageContext.request.contextPath}/images/computer.png" alt="Logo Linh Kiện Máy Tính"
                   class="logo-img me-2">
              <span class="brand-name">Linh Kiện Máy Tính</span>
          </a>

            <!-- Nút toggler cho mobile -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Nội dung thanh điều hướng -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto align-items-center">
                    <!-- Form Tìm Kiếm -->
                    <li class="nav-item me-3">
                        <form class="d-flex" action="#" method="get">
                            <input class="form-control me-2" type="search" placeholder="Tìm kiếm sản phẩm" name="query" aria-label="Search">
                            <button class="btn btn-outline-success" type="submit"><i class="bi bi-search"></i></button>
                        </form>
                    </li>

                    <!-- Icon Giỏ Hàng -->
                    <li class="nav-item me-3">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <a class="nav-link position-relative" href="${pageContext.request.contextPath}/cart">
                                    <i class="bi bi-cart-fill" style="font-size: 1.5rem;"></i>
                                    <c:if test="${cartItemCount > 0}">
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                            ${cartItemCount}
                                            <span class="visually-hidden">items in cart</span>
                                        </span>
                                    </c:if>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${pageContext.request.contextPath}/login?redirect=cart">
                                    <i class="bi bi-cart-fill" style="font-size: 1.5rem;"></i>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <!-- Icon Đơn Hàng -->
                    <li class="nav-item me-3">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <a class="nav-link position-relative" href="${pageContext.request.contextPath}/orders">
                                    <i class="bi bi-box-seam" style="font-size: 1.5rem;"></i>
                                    <c:if test="${orderCount > 0}">
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                            ${orderCount}
                                            <span class="visually-hidden">đơn hàng</span>
                                        </span>
                                    </c:if>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${pageContext.request.contextPath}/login?redirect=orders">
                                    <i class="bi bi-box-seam" style="font-size: 1.5rem;"></i>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <!-- Đăng Nhập/Đăng Xuất -->
                    <c:choose>
                       <c:when test="${not empty user}">
                           <li class="nav-item dropdown">
                               <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                   <img src="${pageContext.request.contextPath}${user.profileImage}" alt="User Avatar" class="user-avatar me-2">
                                   ${user.name}
                               </a>
                               <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                   <li>
                                       <a class="dropdown-item d-flex align-items-center" href="#">
                                           <i class="bi bi-person-circle me-2"></i> Hồ sơ
                                       </a>
                                   </li>
                                   <li>
                                       <a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/keyManagement">
                                           <i class="bi bi-key me-2"></i> Quản lý key
                                       </a>
                                   </li>
                               <li>
                                   <a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/toolManagement">
                                       <i class="bi bi-tools me-2"></i> Quản lý tool
                                   </a>
                               </li>

                                   <li>
                                       <a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/logout">
                                           <i class="bi bi-box-arrow-right me-2"></i> Đăng xuất
                                       </a>
                                   </li>
                               </ul>
                           </li>
                       </c:when>

                        <c:otherwise>
                            <li class="nav-item me-3">
                                <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/views/user/login.jsp">Đăng nhập</a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/views/user/register.jsp">Đăng ký</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>


