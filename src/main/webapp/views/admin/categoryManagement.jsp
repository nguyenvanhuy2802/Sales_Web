<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/homeAdmin">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Quản lý loại sản phẩm</li>
    </ol>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4 p-3 bg-light rounded shadow-sm">
        <h1 class="fw-bold text-primary mb-0">Quản lý loại sản phẩm</h1>
    </div>

    <!-- Search and Add Category -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="input-group w-50">
            <span class="input-group-text"><i class="bi bi-search"></i></span>
            <input type="text" class="form-control" id="searchCategory" placeholder="Tìm kiếm loại sản phẩm">
        </div>
        <button class="btn btn-primary" onclick="addCategory()">
            <i class="bi bi-plus-circle"></i> Thêm loại sản phẩm
        </button>
    </div>

    <!-- Category Table -->
    <div class="table-responsive mt-4">
        <c:if test="${empty categoryList}">
            <div class="alert alert-warning text-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i> Không có loại sản phẩm nào!
            </div>
        </c:if>
        <c:if test="${not empty categoryList}">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                    <tr class="text-center">
                        <th scope="col">ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Image</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody id="categoryTableBody">
                    <c:forEach var="category" items="${categoryList}">
                        <tr>
                            <td class="text-center">${category.categoryId}</td>
                            <td>
                                <input type="text" value="${category.name}" id="name_${category.categoryId}" class="form-control">
                            </td>
                            <td>
                                <textarea id="description_${category.categoryId}" class="form-control">${category.description}</textarea>
                            </td>
                            <td class="text-center">
                                <img id="categoryImage_${category.categoryId}"
                                     src="${pageContext.request.contextPath}${category.categoryImage}"
                                     alt="Category Image"
                                     class="rounded shadow-sm"
                                     style="width: 50px; height: 50px; object-fit: cover;">
                            </td>
                            <td>
                                <div class="d-flex justify-content-center gap-2">
                                    <button class="btn btn-success btn-sm" onclick="saveCategory(${category.categoryId})">
                                        <i class="bi bi-check-circle"></i>
                                    </button>
                                    <button class="btn btn-danger btn-sm" onclick="deleteCategory(${category.categoryId})">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>

<script>
    // Add Category Function
    function addCategory() {
        alert('Thêm loại sản phẩm mới!');
        // Logic to show a form for adding new categories
    }

    // Edit Category Function
    function saveCategory(id) {
        alert(`Lưu loại sản phẩm thành công!`);
        // Logic to save category details
    }

    // Delete Category Function
    function deleteCategory(id) {
        if (confirm('Bạn có chắc chắn muốn xóa loại sản phẩm này?')) {
            alert(`Đã xóa loại sản phẩm`);
            // Logic to delete the category
        }
    }
</script>

<%@ include file="adminFooter.jsp" %>
