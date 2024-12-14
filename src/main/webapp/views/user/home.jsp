<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-5">
    <!-- Product Categories Section -->
    <section>
        <h2 class="mb-5 text-center fw-bold">Danh Mục Sản Phẩm</h2>
        <div class="row g-4">
            <c:forEach var="category" items="${categoryList}">
                <div class="col-12 col-sm-6 col-md-4">
                    <a href="${pageContext.request.contextPath}/category?categoryId=${category.categoryId}" class="text-decoration-none">
                        <div class="category-card shadow rounded overflow-hidden"
                             style="background-image: url('${pageContext.request.contextPath}${category.categoryImage}'); background-size: cover; background-position: center;">
                            <div class="card-title-container p-3 bg-dark bg-opacity-50">
                                <h5 class="card-title text-white text-center">${category.name}</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    <!-- Product List Section -->
    <section class="mt-5">
        <h2 class="mb-5 text-center fw-bold">Danh Sách Sản Phẩm</h2>
        <div class="row g-4">
            <c:forEach var="product" items="${productList}">
                <div class="col-12 col-sm-6 col-md-4">
                    <div class="card h-100 shadow border-0 rounded-3 product-card">
                        <a href="${pageContext.request.contextPath}/productDetail?id=${product.productId}" class="text-decoration-none">
                            <img src="${pageContext.request.contextPath}${product.productImage}"
                                 class="card-img-top product-card-img rounded-top"
                                 alt="${product.name}" loading="lazy">
                        </a>
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title text-truncate">${product.name}</h5>
                            <p class="product-description text-muted text-truncate">Mô tả: ${product.description}</p>
                            <div class="mt-3">
                                <div class="product-price fw-bold text-success mb-3">
                                    Giá:
                                    <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" /> VNĐ
                                </div>
                                <div class="btn-group d-flex gap-2">
                                    <button type="button" class="btn btn-outline-primary flex-grow-1"
                                            data-bs-toggle="modal" data-bs-target="#actionModal"
                                            data-action="addToCart" data-id="${product.productId}"
                                            data-name="${product.name}" data-price="${product.price}"
                                            data-image="${product.productImage}">
                                        <i class="bi bi-cart-plus me-2"></i>Thêm vào Giỏ
                                    </button>
                                    <button type="button" class="btn btn-success flex-grow-1"
                                            data-bs-toggle="modal" data-bs-target="#actionModal"
                                            data-action="buyNow" data-id="${product.productId}"
                                            data-name="${product.name}" data-price="${product.price}"
                                            data-image="${product.productImage}">
                                        <i class="bi bi-credit-card me-2"></i>Mua Ngay
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</div>
<!-- Modal Chung cho Mua Ngay và Thêm vào Giỏ Hàng -->
<div class="modal fade" id="actionModal" tabindex="-1" aria-labelledby="actionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form id="actionForm" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="actionModalLabel">Xác nhận</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Thông tin sản phẩm -->
                    <div class="d-flex align-items-center mb-3">
                        <img id="modalProductImage" src="" alt="Product Image" class="img-fluid rounded-circle" style="width: 80px; height: 80px; object-fit: cover; margin-right: 15px;">
                        <div>
                            <h5 id="modalProductName"></h5>
                            <p id="modalProductPrice"></p>
                        </div>
                    </div>
                    <!-- Nhập số lượng với nút giảm và tăng -->
                    <div class="mb-3">
                        <label for="modalQuantity" class="form-label">Số lượng:</label>
                        <div class="input-group">
                            <button type="button" class="btn btn-outline-secondary" id="decreaseQuantity">-</button>
                            <input type="number" class="form-control text-center" id="modalQuantity" name="quantity" value="1" min="1" required>
                            <button type="button" class="btn btn-outline-secondary" id="increaseQuantity">+</button>
                        </div>
                    </div>
                    <!-- Ẩn ID sản phẩm -->
                    <input type="hidden" id="modalProductId" name="productId" value=""/>
                    <!-- Ẩn Hành động -->
                    <input type="hidden" id="modalAction" name="action" value=""/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary" id="modalSubmitButton">Xác nhận</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Include your custom JS for handling the modal -->
<script src="${pageContext.request.contextPath}/js/productModal.js"></script>

<%@ include file="footer.jsp" %>
