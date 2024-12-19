<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="header.jsp" %>
<div class="container mt-5">
    <h2 class="mb-4">Thanh Toán</h2>
    <c:if test="${not empty selectedItems}">
        <!-- Hiển thị danh sách các sản phẩm được chọn -->
        <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout-lot" method="post">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Sản Phẩm</th>
                        <th>Đơn Giá</th>
                        <th>Số Lượng</th>
                        <th>Thành Tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="grandTotal" value="0" />
                    <c:forEach var="item" items="${selectedItems}" varStatus="status">
                        <tr>
                            <th scope="row">${status.count}</th>
                            <td>
                                <img src="${pageContext.request.contextPath}${item.product.productImage}" alt="${item.product.name}" style="width:50px; height:50px; object-fit: cover; margin-right: 10px;">
                                ${item.product.name}
                                <!-- Các trường ẩn để gửi dữ liệu -->
                                <input type="hidden" name="productNames" value="${item.product.name}">
                                <input type="hidden" name="productIds" value="${item.product.productId}">
                                <input type="hidden" name="cartItemIds" value="${item.cartItem.cartItemId}">
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="VND " maxFractionDigits="0" />
                                <input type="hidden" name="unitPrices" value="${item.product.price}">
                            </td>
                            <td>
                                <input type="number" name="quantities" value="${item.cartItem.quantity}" min="1" class="form-control" style="width: 80px;" required>
                            </td>
                            <td>
                                <span class="totalPrice"><fmt:formatNumber value="${item.product.price * item.cartItem.quantity}" type="currency" currencySymbol="VND " maxFractionDigits="0" /></span>
                                <input type="hidden" name="totalAmounts" value="${item.product.price * item.cartItem.quantity}">
                            </td>
                        </tr>
                        <c:set var="grandTotal" value="${grandTotal + (item.product.price * item.cartItem.quantity)}" />
                    </c:forEach>
                </tbody>
            </table>

            <!-- Tổng giá tiền -->
            <div class="d-flex justify-content-end">
                <h4>Tổng Cộng: <strong><fmt:formatNumber  value="${grandTotal}" type="currency" currencySymbol="VND " maxFractionDigits="0" /></strong></h4>
            </div>

            <input type="hidden" name="grandTotal" id="grandTotalHidden" value="${grandTotal}">

            <!-- Thông Tin Người Dùng -->
            <div class="mt-4">
                <!-- Họ và Tên -->
                <div class="mb-3">
                    <label for="fullName" class="form-label">Họ và Tên</label>
                    <input type="text" class="form-control" id="fullName" name="fullName"
                           value="${not empty user ? fn:escapeXml(user.name) : ''}" required>
                </div>

                <!-- Địa Chỉ Giao Hàng -->
                <div class="mb-3">
                    <label for="address" class="form-label">Địa chỉ giao hàng</label>
                    <textarea class="form-control" id="address" name="address" rows="3" required>${not empty user ? fn:escapeXml(user.address) : ''}</textarea>
                </div>

                <!-- Số Điện Thoại -->
                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="tel" class="form-control" id="phone" name="phone"
                           value="${not empty user ? fn:escapeXml(user.phone) : ''}" required pattern="[0-9]{10}">
                    <div class="form-text">Vui lòng nhập 10 chữ số.</div>
                </div>

                <!-- Phương Thức Thanh Toán -->
                <div class="mb-3">
                    <label for="paymentMethod" class="form-label">Phương thức thanh toán</label>
                    <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                        <option value="cod">Thanh toán khi nhận hàng (COD)</option>
                        <option value="creditCard">Thẻ tín dụng</option>
                        <option value="paypal">PayPal</option>
                    </select>
                </div>
            </div>

            <!-- Nút Xác Nhận Thanh Toán -->
            <button type="submit" class="btn btn-primary">Xác nhận thanh toán</button>
        </form>
    </c:if>
    <c:if test="${empty selectedItems}">
        <div class="alert alert-warning">Không có sản phẩm nào được chọn để thanh toán.</div>
    </c:if>
</div>

<script src="${pageContext.request.contextPath}/js/payLotNotice.js"></script>
<%@ include file="footer.jsp" %>
