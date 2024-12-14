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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerstyles.css">

</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
        <div class="container-fluid">
            <!-- Logo và tên thương hiệu -->
            <a class="navbar-brand fw-bold d-flex align-items-center" href="${pageContext.request.contextPath}/homeAdmin">
                <img src="${pageContext.request.contextPath}/images/computer.png" alt="Logo Linh Kiện Máy Tính"
                     class="logo-img me-2">
                <span class="brand-name">Admin Dashboard</span>
            </a>

            <!-- Nội dung thanh điều hướng -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto align-items-center">
                    <!-- Kiểm tra nếu có admin trong session -->
                    <c:if test="${not empty admin}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <img src="${pageContext.request.contextPath}${admin.profileImage}" alt="User Avatar" class="user-avatar me-2">
                                ${admin.name}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Hồ sơ</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logoutAdmin">Đăng xuất</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

