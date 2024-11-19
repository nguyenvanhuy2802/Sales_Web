<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

<div class="container mt-4">
    <h2>Giỏ Hàng Của Bạn</h2>

    <c:choose>
        <c:when test="${not empty cartItemList}">
            <!-- Bảng sản phẩm không nằm trong thẻ <form> -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Chọn</th>
                        <th scope="col">Sản Phẩm</th>
                        <th scope="col">Số Lượng</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Thành Tiền</th>
                        <th scope="col">Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItemList}" varStatus="status">
                        <!-- Thêm các thuộc tính dữ liệu cho mỗi hàng -->
                        <tr data-price="${item.product.price}" data-quantity="${item.cartItem.quantity}">
                            <th scope="row">${status.count}</th>
                            <td>
                                <!-- Checkbox được liên kết với buyNowForm -->
                                <input type="checkbox" name="selectedCartItemIds" value="${item.cartItem.cartItemId}" class="cartItem-checkbox" form="buyNowForm">
                            </td>
                            <td>
                                <img src="${pageContext.request.contextPath}${item.product.productImage}" alt="${item.product.name}" style="width:50px; height:50px; object-fit: cover; margin-right: 10px;">
                                ${item.product.name}
                            </td>
                            <td>
                                <!-- Form riêng cho cập nhật số lượng -->
                                <form action="${pageContext.request.contextPath}/updateQuantity" method="post" class="d-inline">
                                    <input type="hidden" name="productId" value="${item.product.productId}">
                                    <input type="number" name="quantity" value="${item.cartItem.quantity}" min="1" class="form-control d-inline" style="width: 60px;">
                                    <button type="submit" class="btn btn-primary btn-sm">Cập Nhật</button>
                                </form>
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.product.price}" type="number" groupingUsed="true" />
                                VNĐ
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.product.price * item.cartItem.quantity}" type="number" groupingUsed="true" />
                                VNĐ
                            </td>
                            <td>
                                <!-- Form riêng cho xóa sản phẩm -->
                                <form action="${pageContext.request.contextPath}/removeFromCart" method="post" class="d-inline">
                                    <input type="hidden" name="productId" value="${item.product.productId}">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Thẻ <form> riêng cho hành động Buy Now -->
            <form id="buyNowForm" action="${pageContext.request.contextPath}/buyALot" method="post">
                <!-- Tổng giá tiền -->
                <div class="d-flex justify-content-end">
                    <h4>Tổng Cộng:
                        <strong id="totalPrice">
                            0 VNĐ
                        </strong>
                    </h4>
                </div>

                <div class="d-flex justify-content-end mt-3">
                    <!-- Nút Tiến Hành Thanh Toán nằm trong form riêng -->
                    <button type="submit" class="btn btn-success">Tiến Hành Thanh Toán</button>
                </div>
            </form>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">Giỏ hàng của bạn đang trống.</div>
        </c:otherwise>
    </c:choose>

    <!-- Nút Quay lại Trang Chủ luôn hiển thị -->
    <div class="d-flex justify-content-end mt-3">
        <a href="${pageContext.request.contextPath}/product" class="btn btn-secondary me-2">
            <i class="bi bi-house-door-fill"></i> Quay lại Trang Chủ
        </a>
    </div>
</div>

<!-- Thêm JavaScript để cập nhật tổng giá tiền và gửi dữ liệu cho Buy Now -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const totalPriceElement = document.getElementById('totalPrice');
        if (!totalPriceElement) {
            return;
        }

        const checkboxes = document.querySelectorAll('.cartItem-checkbox');
        const buyNowForm = document.getElementById('buyNowForm');
        // Hàm định dạng số theo định dạng Việt Nam
        function formatNumber(num) {
            return new Intl.NumberFormat('vi-VN').format(num) + ' VNĐ';
        }

        // Hàm cập nhật tổng giá tiền
        function updateTotal() {
            let total = 0;
            checkboxes.forEach(function(checkbox) {
                if (checkbox.checked) {
                    const tr = checkbox.closest('tr');
                    const price = parseFloat(tr.getAttribute('data-price'));
                    const quantity = parseInt(tr.getAttribute('data-quantity'));
                    total += price * quantity;
                }
            });
            totalPriceElement.textContent = formatNumber(total);
        }

        // Thêm sự kiện lắng nghe cho mỗi checkbox
        checkboxes.forEach(function(checkbox) {
            checkbox.addEventListener('change', updateTotal);
        });

        // Khởi tạo tổng giá tiền khi tải trang
        updateTotal();

        // Khi submit form Buy Now, đảm bảo chỉ gửi các sản phẩm được chọn
        buyNowForm.addEventListener('submit', function(event) {
            // Không cần thêm gì, vì chỉ các checkbox liên kết với form này sẽ được gửi
            // Nhưng nếu cần, có thể kiểm tra xem ít nhất một sản phẩm đã được chọn
            let anyChecked = false;
            checkboxes.forEach(function(checkbox) {
                if (checkbox.checked) {
                    anyChecked = true;
                }
            });
            if (!anyChecked) {
                event.preventDefault();
                alert('Vui lòng chọn ít nhất một sản phẩm để tiến hành thanh toán.');
            }
        });
    });
</script>

<%@ include file="footer.jsp" %>
