    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


    <%@ include file="header.jsp" %>
      <div class="container mt-5">
            <h2 class="mb-4">Thanh Toán</h2>
            <c:if test="${not empty product}">
                <div class="card mb-4">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${pageContext.request.contextPath}${product.productImage}" class="img-fluid rounded-start" alt="${product.name}">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">${product.name}</h5>
                                   <p class="card-text">
                                       Giá:
                                       <span id="unitPrice" data-unit-price="${product.price}">
                                           <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="VND " maxFractionDigits="0" />
                                       </span>
                                   </p>
                                <!-- Thay đổi trường số lượng từ văn bản tĩnh thành trường nhập liệu với nút tăng giảm -->
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Số lượng</label>
                                    <div class="input-group">
                                        <button class="btn btn-outline-secondary" type="button" id="button-decrease">-</button>
                                        <input type="number" class="form-control text-center" id="quantity" name="quantity" value="${quantity}" min="1" required>
                                        <button class="btn btn-outline-secondary" type="button" id="button-increase">+</button>
                                    </div>
                                </div>
                                <p class="card-text">Tổng cộng: <span id="totalPrice"><fmt:formatNumber value="${product.price * quantity}" type="currency" currencySymbol="VND " /></span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="post">
                <input type="hidden" name="productId" value="${product.productId}">
                <input type="hidden" name="productName" value="${product.name}">
                <input type="hidden" name="unitPrice" value="${product.price}">
                <input type="hidden" name="quantity" id="hiddenQuantity" value="${quantity}">
                <input type="hidden" name="totalAmount" id="hiddenTotalAmount" value="${product.price * quantity}">

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

                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="text" class="form-control" id="email" name="email"
                        value="${not empty user ? fn:escapeXml(user.email) : ''}" required>
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

                <!-- Nút Xác Nhận Thanh Toán -->
                <button type="submit" class="btn btn-primary">Xác nhận thanh toán</button>
            </form>
        </div>

    <script src="${pageContext.request.contextPath}/js/payNotice.js"></script>


    <%@ include file="footer.jsp" %>
