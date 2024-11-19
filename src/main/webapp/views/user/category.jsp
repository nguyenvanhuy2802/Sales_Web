<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/product">Trang chủ</a></li>
<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/category?categoryId=${product.categoryId}">${categoryName}</a></li>
  </ol>
</nav>

<div class="container mt-4">
    <h1 class="mb-4">Danh Sách Sản Phẩm - ${categoryName}</h1>

    <!-- Bắt đầu hàng chứa các sản phẩm -->
    <div class="row">
        <c:forEach var="product" items="${productList}">
            <div class="col-md-4 mb-4">
                <div class="card product-card">
                    <a href="${pageContext.request.contextPath}/productDetail?id=${product.productId}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${pageContext.request.contextPath}${product.productImage}" class="card-img-top product-card-img" alt="${product.name}">
                    </a>

                    <div class="card-body product-card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="product-description">${product.description}</p>
                        <div class="product-price">
                            <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />
                            VNĐ
                        </div>

                        <!-- Nhóm nút hành động -->
                        <div class="btn-group">
                            <c:choose>
                                <c:when test="${not empty user}">
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
        </c:forEach>
    </div>
    <!-- Kết thúc hàng chứa các sản phẩm -->
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
                        <img id="modalProductImage" src="" alt="Product Image" class="img-fluid" style="width: 100px; height: 100px; object-fit: cover; margin-right: 15px;">
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
                    <button type="submit" class="btn" id="modalSubmitButton">Xác nhận</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/productModal.js"></script>
<%@ include file="footer.jsp" %>
