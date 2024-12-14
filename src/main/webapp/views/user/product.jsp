<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
  <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/product" class="text-decoration-none text-primary">Trang chủ</a></li>
    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/category?categoryId=${product.categoryId}" class="text-decoration-none text-primary">${categoryName}</a></li>
    <li class="breadcrumb-item active text-secondary" aria-current="page">${product.name}</li>
  </ol>
</nav>

<c:if test="${not empty product}">
  <div class="container py-5 bg-light shadow-lg rounded">
    <div class="row g-4">
      <!-- Hình ảnh sản phẩm -->
      <div class="col-lg-5 col-md-6">
        <div id="productCarousel" class="carousel slide rounded overflow-hidden shadow" data-bs-ride="carousel">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <img src="${pageContext.request.contextPath}${product.productImage}" class="d-block w-100" alt="${product.name}" style="max-height: 500px; object-fit: contain;">
            </div>
          </div>
          <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>

      <!-- Chi tiết sản phẩm -->
      <div class="col-lg-7 col-md-6">
        <h2 class="fw-bold text-primary mb-3">${product.name}</h2>

        <!-- Price Section -->
        <div class="d-flex align-items-center mb-3">
          <h4 class="text-danger fs-3 mb-0 me-3">
            Giá: <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" /> VNĐ
          </h4>
        </div>

        <!-- Description Section -->
        <p class="text-muted mb-4">
          <strong>Mô tả:</strong> ${product.description}
        </p>

        <!-- Stock Quantity Section -->
        <p class="fw-bold mb-4">
          Số lượng tồn kho: <span class="badge bg-success">${product.stockQuantity}</span>
        </p>

        <!-- Action Buttons -->
        <div class="d-flex gap-3 mt-4">
          <button type="button" class="btn btn-outline-primary rounded-pill px-4 py-2 shadow-sm"
                  data-bs-toggle="modal" data-bs-target="#actionModal"
                  data-action="addToCart"
                  data-id="${product.productId}"
                  data-name="${product.name}"
                  data-price="${product.price}"
                  data-image="${product.productImage}">
            <i class="bi bi-cart-plus me-2"></i> Thêm vào Giỏ
          </button>
          <button type="button" class="btn btn-success rounded-pill px-4 py-2 shadow-sm"
                  data-bs-toggle="modal" data-bs-target="#actionModal"
                  data-action="buyNow"
                  data-id="${product.productId}"
                  data-name="${product.name}"
                  data-price="${product.price}"
                  data-image="${product.productImage}">
            <i class="bi bi-credit-card me-2"></i> Mua Ngay
          </button>
        </div>
      </div>
    </div>
  </div>
</c:if>


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
