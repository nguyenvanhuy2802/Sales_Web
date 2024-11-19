<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div class="container mt-5">
    <h1 class="mb-4">Đơn Hàng Của Bạn</h1>

    <!-- Tab navigation -->
    <ul class="nav nav-tabs" id="orderTab" role="tablist">
        <li class="nav-item" role="presentation">
            <a class="nav-link active" id="pendingOrdersTab" data-bs-toggle="tab" href="#pendingOrders" role="tab" aria-controls="pendingOrders" aria-selected="true">Đang chờ</a>
        </li>
        <li class="nav-item" role="presentation">
            <a class="nav-link" id="cancelledOrdersTab" data-bs-toggle="tab" href="#cancelledOrders" role="tab" aria-controls="cancelledOrders" aria-selected="false">Đã Hủy</a>
        </li>
    </ul>

    <!-- Tab content -->
    <div class="tab-content mt-3">
        <!-- Đơn hàng chưa xử lý (Pending Orders) -->
        <div class="tab-pane fade show active" id="pendingOrders" role="tabpanel" aria-labelledby="pendingOrdersTab">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Ngày Đặt</th>
                        <th>Trạng Thái</th>
                        <th>Tổng Tiền</th>
                        <th>Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Loop through pending orders -->
                    <c:forEach var="order" items="${pendingOrders}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                            <td>${order.status}</td>
                            <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VNĐ</td>
                            <td>
                                <button class="btn btn-primary btn-sm view-details-btn" data-order-id="${order.orderId}">
                                    <i class="bi bi-eye"></i> Xem Chi Tiết
                                </button>
                                <button class="btn btn-danger btn-sm cancel-order-btn" data-order-id="${order.orderId}">
                                    <i class="bi bi-x-circle"></i> Hủy Đơn Hàng
                                </button>
                            </td>

                        </tr>
                         <tr id="orderDetails${order.orderId}" class="collapse">
                                <td colspan="5">
                                    <!-- Phần hiển thị danh sách sản phẩm sẽ được tải vào đây -->
                                    <div class="order-items-container"></div>
                                </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Đơn hàng đã hủy (Cancelled Orders) -->
        <div class="tab-pane fade" id="cancelledOrders" role="tabpanel" aria-labelledby="cancelledOrdersTab">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Ngày Đặt</th>
                        <th>Trạng Thái</th>
                        <th>Tổng Tiền</th>
                        <th>Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Loop through cancelled orders -->
                    <c:forEach var="order" items="${cancelledOrders}">
                       <tr>
                           <td>${order.orderId}</td>
                           <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                           <td>${order.status}</td>
                           <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VNĐ</td>
                           <td>
                               <button class="btn btn-primary btn-sm view-details-btn" data-order-id="${order.orderId}">
                                   <i class="bi bi-eye"></i> Xem Chi Tiết
                               </button>

                           </td>

                       </tr>
                        <tr id="orderDetails${order.orderId}" class="collapse">
                               <td colspan="5">
                                   <!-- Phần hiển thị danh sách sản phẩm sẽ được tải vào đây -->
                                   <div class="order-items-container"></div>
                               </td>
                           </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

     <div class="d-flex justify-content-end mt-3">
            <a href="${pageContext.request.contextPath}/product" class="btn btn-secondary me-2">
                <i class="bi bi-house-door-fill"></i> Quay lại Trang Chủ
            </a>
        </div>
</div>

<script src="${pageContext.request.contextPath}/js/orderDetails.js"></script>
<%@ include file="footer.jsp" %>
