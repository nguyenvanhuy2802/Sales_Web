<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/homeAdmin">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Quản lý sản phẩm</li>
    </ol>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4 p-3 bg-light rounded shadow-sm">
        <h1 class="fw-bold text-primary mb-0">Quản Lý Sản Phẩm</h1>
    </div>

    <!-- Search and Add Product -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="input-group w-50">
            <span class="input-group-text"><i class="bi bi-search"></i></span>
            <input type="text" class="form-control" id="searchProduct" placeholder="Tìm kiếm sản phẩm">
        </div>
        <button class="btn btn-primary" onclick="addProduct()">
            <i class="bi bi-plus-circle"></i> Thêm sản phẩm
        </button>
    </div>

    <div class="table-responsive mt-4">
        <c:if test="${empty productList}">
            <div class="alert alert-warning text-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i> Không có sản phẩm nào!
            </div>
        </c:if>
        <c:if test="${not empty productList}">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                    <tr class="text-center">
                        <th scope="col">ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Price</th>
                        <th scope="col">Stock Quantity</th>
                        <th scope="col">Category ID</th>
                        <th scope="col">Product Image</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody id="productTableBody">
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td class="text-center">${product.productId}</td>
                            <td>
                                <input type="text" value="${product.name}" id="name_${product.productId}" class="form-control">
                            </td>
                            <td>
                                <textarea id="description_${product.productId}" class="form-control">${product.description}</textarea>
                            </td>
                            <td>
                                <input type="number" value="${product.price}" id="price_${product.productId}" class="form-control">
                            </td>
                            <td>
                                <input type="number" value="${product.stockQuantity}" id="stockQuantity_${product.productId}" class="form-control">
                            </td>
                            <td>
                                <input type="number" value="${product.categoryId}" id="categoryId_${product.productId}" class="form-control">
                            </td>
                            <td class="text-center">
                                <img id="productImage_${product.productId}"
                                     src="${pageContext.request.contextPath}${product.productImage}"
                                     alt="Product Image"
                                     class="rounded shadow-sm"
                                     style="width: 50px; height: 50px; object-fit: cover;">
                            </td>
                            <td>
                                <div class="d-flex justify-content-center gap-2">
                                    <button class="btn btn-success btn-sm" onclick="saveProduct(${product.productId})">
                                        <i class="bi bi-check-circle"></i>
                                    </button>
                                    <button class="btn btn-danger btn-sm" onclick="deleteProduct(${product.productId})">
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
    // Add Product Function
    function addProduct() {
        alert('Thêm sản phẩm mới!');
        // Logic to show a form for adding new products
    }

    // Save Product Function
    function saveProduct(id) {
        alert(`Lưu sản phẩm thành công`);
        // Logic to save product details
    }

    // Delete Product Function
    function deleteProduct(id) {
        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
            alert(`Đã xóa sản phẩm`);
            // Logic to delete the product
        }
    }
</script>

<%@ include file="adminFooter.jsp" %>
