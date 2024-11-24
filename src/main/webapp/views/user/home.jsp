<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Custom CSS for additional styling -->
<style>
    /* Category Card Styling */
    .category-card {
        position: relative;
        width: 100%;
        padding-top: 75%; /* 4:3 Aspect Ratio */
        background-size: cover;
        background-position: center;
        border-radius: 0.5rem;
        overflow: hidden;
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .category-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0,0,0,0.2);
    }

    .category-card::after {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.3);
        transition: background 0.3s;
    }

    .category-card:hover::after {
        background: rgba(0, 0, 0, 0.5);
    }

    .card-title-container {
        position: absolute;
        bottom: 0;
        width: 100%;
        padding: 0.75rem;
        background: rgba(255, 255, 255, 0.8);
        text-align: center;
    }

    .card-title-container h5 {
        margin: 0;
        color: #333;
        font-weight: 600;
    }

    /* Product Card Styling */
    .product-card {
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0,0,0,0.2);
    }

    .product-card-img {
        height: 200px;
        object-fit: cover;
        border-top-left-radius: 0.5rem;
        border-top-right-radius: 0.5rem;
    }

    .product-description {
        height: 60px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 3; /* Number of lines to show */
        -webkit-box-orient: vertical;
    }

    .product-price {
        font-size: 1.25rem;
        font-weight: bold;
        color: #28a745;
    }

    /* Action Buttons Styling */
    .action-btn {
        width: 100%;
        margin-bottom: 0.5rem;
    }

    @media (min-width: 768px) {
        .action-btn {
            width: auto;
            margin-right: 0.5rem;
            margin-bottom: 0;
        }
    }

    /* Modal Image Styling */
    #modalProductImage {
        width: 100px;
        height: 100px;
        object-fit: cover;
        border-radius: 0.25rem;
        margin-right: 15px;
    }

    /* Adjusting the modal for better spacing */
    .modal-body {
        padding: 1.5rem;
    }
</style>

<div class="container mt-4">
    <!-- Product Categories Section -->
    <section>
        <h2 class="mb-4 text-center">Danh Mục Sản Phẩm</h2>

        <div class="row g-4">
            <c:forEach var="category" items="${categoryList}">
                <div class="col-12 col-sm-6 col-md-4">
                    <a href="${pageContext.request.contextPath}/category?categoryId=${category.categoryId}" class="text-decoration-none">
                        <div class="category-card" style="background-image: url('${pageContext.request.contextPath}${category.categoryImage}');">
                            <div class="card-title-container">
                                <h5 class="card-title">${category.name}</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    <!-- Product List Section -->
    <section class="mt-5">
        <h2 class="mb-4 text-center">Danh Sách Sản Phẩm</h2>

        <div class="row g-4">
            <c:forEach var="product" items="${productList}">
                <div class="col-12 col-sm-6 col-md-4">
                    <div class="card h-100 product-card">
                        <a href="${pageContext.request.contextPath}/productDetail?id=${product.productId}" class="text-decoration-none">
                            <img src="${pageContext.request.contextPath}${product.productImage}" class="card-img-top product-card-img" alt="${product.name}" loading="lazy">
                        </a>

                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="product-description">${product.description}</p>
                            <div class="mt-auto">
                                <div class="product-price mb-3">
                                    <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" /> VNĐ
                                </div>

                                <div class="d-grid gap-2">
                                    <c:choose>
                                        <c:when test="${not empty user}">
                                            <!-- Nút Mua Ngay -->
                                            <button type="button" class="btn btn-success action-btn"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#actionModal"
                                                    data-action="buyNow"
                                                    data-id="${product.productId}"
                                                    data-name="${product.name}"
                                                    data-price="${product.price}"
                                                    data-image="${product.productImage}">
                                                Mua Ngay
                                            </button>
                                            <!-- Nút Thêm vào Giỏ Hàng -->
                                            <button type="button" class="btn btn-primary action-btn"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#actionModal"
                                                    data-action="addToCart"
                                                    data-id="${product.productId}"
                                                    data-name="${product.name}"
                                                    data-price="${product.price}"
                                                    data-image="${product.productImage}">
                                                Thêm vào Giỏ
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Nút Mua Ngay (yêu cầu đăng nhập) -->
                                            <button type="button" class="btn btn-success action-btn"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#actionModal"
                                                    data-action="buyNow"
                                                    data-id="${product.productId}"
                                                    data-name="${product.name}"
                                                    data-price="${product.price}"
                                                    data-image="${product.productImage}">
                                                Mua Ngay
                                            </button>
                                            <!-- Nút Thêm vào Giỏ Hàng (yêu cầu đăng nhập) -->
                                            <button type="button" class="btn btn-primary action-btn"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#actionModal"
                                                    data-action="addToCart"
                                                    data-id="${product.productId}"
                                                    data-name="${product.name}"
                                                    data-price="${product.price}"
                                                    data-image="${product.productImage}">
                                                Thêm vào Giỏ
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</div>

<!-- Action Modal -->
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
                        <img id="modalProductImage" src="" alt="Product Image" class="img-fluid">
                        <div>
                            <h5 id="modalProductName" class="mb-1"></h5>
                            <p id="modalProductPrice" class="text-muted"></p>
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
                    <!-- Ẩn ID sản phẩm và Hành động -->
                    <input type="hidden" id="modalProductId" name="productId" value="">
                    <input type="hidden" id="modalAction" name="action" value="">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary" id="modalSubmitButton">Xác nhận</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Include Bootstrap JS (Ensure you have Bootstrap 5.3.0) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Include your custom JS for handling the modal -->
<script src="${pageContext.request.contextPath}/js/productModal.js"></script>

<%@ include file="footer.jsp" %>
