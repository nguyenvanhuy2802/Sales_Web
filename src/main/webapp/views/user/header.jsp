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


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/images/computer.png" type="image/png">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
        }
        .collapse {
            display: none;
        }

        .collapse.show {
            display: table-row;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/product">Linh Kiện Máy Tính</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <!-- Form Tìm Kiếm -->
                    <form class="d-flex me-3" action="search" method="get">
                        <input class="form-control me-2" type="search" placeholder="Tìm kiếm sản phẩm" name="query" aria-label="Search">
                        <button class="btn btn-outline-success" type="submit">Tìm kiếm</button>
                    </form>

                    <!-- Icon Giỏ Hàng -->
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <a class="nav-link position-relative" href="${pageContext.request.contextPath}/cart">
                                    <span class="bi bi-cart-fill"></span>
                                    <c:if test="${cartItemCount > 0}">
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                            ${cartItemCount}
                                            <span class="visually-hidden">unread messages</span>
                                        </span>
                                    </c:if>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${pageContext.request.contextPath}/login?redirect=cart">
                                    <span class="bi bi-cart-fill"></span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>

                  <!-- Icon Đơn Hàng -->
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <a class="nav-link position-relative" href="${pageContext.request.contextPath}/orders">
                                    <span class="bi bi-box-seam"></span>
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
                                    <span class="bi bi-box-seam"></span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <!-- Đăng Nhập/Đăng Xuất -->
                    <c:choose>
                        <c:when test="${not empty user}">
                            <li class="nav-item">
                                <a class="nav-link" href="#">Xin chào, ${user.getUsername()}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/views/user/login.jsp">Đăng nhập</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/views/user/register.jsp">Đăng ký</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
