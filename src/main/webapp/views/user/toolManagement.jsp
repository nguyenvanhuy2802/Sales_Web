<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/product" class="text-decoration-none">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Quản lý tool</li>
    </ol>
</nav>

<div class="content-wrapper">
    <div class="container mt-5 d-flex justify-content-center align-items-center">
        <div class="card shadow-lg p-4 text-center" style="max-width: 500px; border-radius: 15px; background: linear-gradient(135deg, #ffffff, #f8f9fa);">
            <div class="card-body">
                <h2 class="card-title text-primary mb-4"> Downloads</h2>
                <p class="text-muted mb-4">Tải xuống công cụ để ký chữ ký điện tử an toàn và hiệu quả.</p>
                <div class="d-flex justify-content-center mb-3">
                    <i class="bi bi-download-circle text-success" style="font-size: 5rem; animation: bounce 2s infinite;"></i>
                </div>
                <a href="${pageContext.request.contextPath}/downloadTool" class="btn btn-gradient btn-lg px-5" style="background: linear-gradient(90deg, #1de9b6, #1dc4e9); color: white; border: none; border-radius: 50px; transition: transform 0.3s;">
                    <i class="bi bi-download me-2"></i> Tải xuống SignatureTool (.exe)
                </a>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

<style>
    .btn-gradient:hover {
        transform: scale(1.1);
    }

    @keyframes bounce {
        0%, 20%, 50%, 80%, 100% {
            transform: translateY(0);
        }
        40% {
            transform: translateY(-10px);
        }
        60% {
            transform: translateY(-5px);
        }
    }
</style>
