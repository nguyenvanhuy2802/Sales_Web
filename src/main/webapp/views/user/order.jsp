<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/product">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Đơn hàng của bạn</li>
    </ol>
</nav>

<div class="content-wrapper">
    <div class="container mt-5">
    <div class="d-flex align-items-center justify-content-between mb-4">
        <h1>
            Đơn Hàng Của Bạn
        </h1>
     <div class="notification-icon position-relative">
         <i class="bi bi-bell-fill fs-3"></i>
         <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
             ${notificationCount}
         </span>
     </div>
    </div>


        <!-- Tab navigation -->
        <ul class="nav nav-tabs" id="orderTab" role="tablist">
            <li class="nav-item" role="presentation">
                <a class="nav-link active" id="pendingOrdersTab" data-bs-toggle="tab" href="#pendingOrders" role="tab" aria-controls="pendingOrders" aria-selected="true">Đang chờ</a>
            </li>
             <li class="nav-item" role="presentation">
                <a class="nav-link" id="completeOrdersTab" data-bs-toggle="tab" href="#completeOrders" role="tab" aria-controls="completeOrders" aria-selected="false">Đã xử lý</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="cancelledOrdersTab" data-bs-toggle="tab" href="#cancelledOrders" role="tab" aria-controls="cancelledOrders" aria-selected="false">Đã Hủy</a>
            </li>
        </ul>

        <!-- Tab content -->
        <div class="tab-content mt-3">
            <!-- Đơn hàng chưa xử lý (Pending Orders) -->
            <div class="tab-pane fade show active" id="pendingOrders" role="tabpanel" aria-labelledby="pendingOrdersTab">
                <c:if test="${empty pendingOrders}">
                    <!-- Empty state using Bootstrap alert -->
                    <div class="alert alert-info" role="alert">
                        <i class="bi bi-exclamation-triangle-fill"></i> Bạn chưa có đơn hàng nào đang chờ xử lý.
                    </div>
                </c:if>
               <c:if test="${not empty pendingOrders}">
                   <table class="table table-hover">
                       <thead class="table-dark">
                           <tr>
                               <th>Mã Đơn Hàng</th>
                               <th>Ngày Đặt</th>
                               <th>Tổng Tiền</th>
                               <th>Người Mua</th>
                               <th>Địa Chỉ Giao</th>
                               <th>Thao Tác</th>
                           </tr>
                       </thead>
                       <tbody>
                           <!-- Loop through pending orders -->
                           <c:forEach var="order" items="${pendingOrders}">
                               <tr>
                                   <td>${order.orderId}</td>
                                   <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                                   <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VNĐ</td>
                                   <td>${order.buyerName}</td>
                                   <td>${order.deliveryAddress}</td>
                                   <td>
                                       <button class="btn btn-primary btn-sm view-details-btn" data-order-id="${order.orderId}">
                                           <i class="bi bi-eye"></i> Xem Chi Tiết
                                       </button>
                                       <button class="btn btn-danger btn-sm cancel-order-btn" data-order-id="${order.orderId}">
                                           <i class="bi bi-x-circle"></i> Hủy Đơn Hàng
                                       </button>
                                       <button class="btn btn-success btn-sm copy-hash-btn" data-order-id="${order.orderId}" data-hashcode="${order.hashCode}">
                                           <i class="bi bi-clipboard"></i> Coppy hash
                                       </button>
                                       <!-- Toast Notification -->
                                       <div class="toast-container position-fixed bottom-0 end-0 p-3">
                                           <div id="successToast" class="toast bg-success text-white align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
                                               <div class="d-flex">
                                                   <div class="toast-body" id="toastMessage"></div>
                                                   <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                                               </div>
                                           </div>
                                       </div>
                                   </td>
                               </tr>
                               <tr id="orderDetails${order.orderId}" class="collapse">
                                   <td colspan="7">
                                       <!-- Phần hiển thị danh sách sản phẩm sẽ được tải vào đây -->
                                       <div class="order-items-container"></div>
                                   </td>
                               </tr>
                           </c:forEach>
                       </tbody>
                   </table>
               </c:if>

            </div>

<!-- Đơn hàng đã xử lý (Complete Orders) -->
<div class="tab-pane fade" id="completeOrders" role="tabpanel" aria-labelledby="completeOrdersTab">
    <c:if test="${empty completeOrders}">
        <!-- Empty state for complete orders -->
        <div class="alert alert-success" role="alert">
            <i class="bi bi-check-circle-fill"></i> Bạn chưa có đơn hàng nào đã xử lý.
        </div>
    </c:if>
    <c:if test="${not empty completeOrders}">
        <table class="table table-hover">
            <thead class="table-dark">
                <tr>
                  <th>Mã Đơn Hàng</th>
                  <th>Ngày Đặt</th>
                  <th>Tổng Tiền</th>
                  <th>Người Mua</th>
                  <th>Địa Chỉ Giao</th>
                  <th>Thao Tác</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop through complete orders -->
                <c:forEach var="order" items="${completeOrders}">
                    <tr>
                       <td>${order.orderId}</td>
                       <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                       <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VNĐ</td>
                       <td>${order.buyerName}</td>
                       <td>${order.deliveryAddress}</td>
                    <td>
                            <button class="btn btn-primary btn-sm view-details-btn" data-order-id="${order.orderId}">
                                <i class="bi bi-eye"></i> Xem Chi Tiết
                            </button>
                        </td>
                    </tr>
                    <tr id="orderDetails${order.orderId}" class="collapse">
                        <td colspan="7">
                            <!-- Phần hiển thị danh sách sản phẩm sẽ được tải vào đây -->
                            <div class="order-items-container"></div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>


            <!-- Đơn hàng đã hủy (Cancelled Orders) -->
            <div class="tab-pane fade" id="cancelledOrders" role="tabpanel" aria-labelledby="cancelledOrdersTab">
                <c:if test="${empty cancelledOrders}">
                    <!-- Empty state for cancelled orders -->
                    <div class="alert alert-warning" role="alert">
                        <i class="bi bi-x-circle-fill"></i> Bạn chưa có đơn hàng nào đã bị hủy.
                    </div>
                </c:if>
                <c:if test="${not empty cancelledOrders}">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                              <th>Mã Đơn Hàng</th>
                              <th>Ngày Đặt</th>
                              <th>Tổng Tiền</th>
                              <th>Người Mua</th>
                              <th>Địa Chỉ Giao</th>
                              <th>Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Loop through cancelled orders -->
                            <c:forEach var="order" items="${cancelledOrders}">
                               <tr>
                                 <td>${order.orderId}</td>
                                 <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                                 <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VNĐ</td>
                                 <td>${order.buyerName}</td>
                                 <td>${order.deliveryAddress}</td>
                               <td>
                                       <button class="btn btn-primary btn-sm view-details-btn" data-order-id="${order.orderId}">
                                           <i class="bi bi-eye"></i> Xem Chi Tiết
                                       </button>
                                   </td>
                               </tr>
                                <tr id="orderDetails${order.orderId}" class="collapse">
                                       <td colspan="7">
                                           <!-- Phần hiển thị danh sách sản phẩm sẽ được tải vào đây -->
                                           <div class="order-items-container"></div>
                                       </td>
                                   </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>

        <div class="d-flex justify-content-end mt-3">
            <a href="${pageContext.request.contextPath}/product" class="btn btn-secondary me-2">
                <i class="bi bi-house-door-fill"></i> Quay lại Trang Chủ
            </a>
        </div>

    </div>
</div>

<!-- Modal thông báo -->
<div class="modal fade" id="notificationModal" tabindex="-1" aria-labelledby="notificationModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="notificationModalLabel">Thông báo đơn hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <ul class="list-group" id="notificationList">
                    <!-- Các đơn hàng sẽ được thêm vào đây -->
                </ul>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/showNotifications.js"></script>
<script src="${pageContext.request.contextPath}/js/orderDetails.js"></script>
<%@ include file="footer.jsp" %>
