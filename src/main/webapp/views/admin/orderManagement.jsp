<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/homeAdmin">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Quản lý đơn hàng</li>
    </ol>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4 p-3 bg-light rounded shadow-sm">
        <h1 class="fw-bold text-primary mb-0">Quản Lý Đơn Hàng</h1>
    </div>

    <!-- Search and Add Order -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="input-group w-50">
            <span class="input-group-text"><i class="bi bi-search"></i></span>
            <input type="text" class="form-control" id="searchOrder" placeholder="Tìm kiếm đơn hàng">
        </div>
    </div>

    <div class="table-responsive mt-4">
        <c:if test="${empty orderList}">
            <div class="alert alert-warning text-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i> Không có đơn hàng!
            </div>
        </c:if>

        <c:if test="${not empty orderList}">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                    <tr class="text-center">
                        <th scope="col">ID</th>
                        <th scope="col">Customer ID</th>
                        <th scope="col">Order Date</th>
                        <th scope="col">Status</th>
                        <th scope="col">Total Amount</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody id="orderTableBody">
                    <c:forEach var="order" items="${orderList}" varStatus="status">
                        <tr>
                            <!-- Order ID (Read-only) -->
                            <td class="text-center">${order.orderId}</td>

                            <!-- Customer ID (Read-only) -->
                            <td>
                                <input type="text" value="${order.customerId}" class="form-control" readonly>
                            </td>

                            <!-- Order Date (Read-only) -->
                            <td>
                                <input type="datetime-local" value="${order.orderDate}" class="form-control" readonly>
                            </td>

                            <!-- Order Status (Editable) -->
                            <td>
                             <select class="form-select order-status">
                                    <option value="pending" ${order.status == 'pending' ? 'selected' : ''}>pending</option>
                                    <option value="complete" ${order.status == 'complete' ? 'selected' : ''}>complete</option>
                                    <option value="canceled" ${order.status == 'canceled' ? 'selected' : ''}>canceled</option>
                                </select>
                            </td>

                            <td>

                            <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true" /> VNĐ
                            </td>

                            <!-- Actions -->
                            <td>
                                <div class="d-flex justify-content-center gap-2">
                                    <button class="btn btn-success btn-sm update-status" data-order-id="${order.orderId}">
                                                  <i class="bi bi-check-circle"></i>
                                    </button>
                                    <button class="btn btn-info btn-sm toggle-details">
                                        <i class="bi bi-caret-down-fill"></i>
                                    </button>
                                </div>
                            </td>
                        </tr>

                        <!-- Order Details Row -->
                        <tr class="order-details" style="display: none;">
                            <td colspan="6">
                                <div class="p-3 bg-light order-items-container">
                                    <p class="text-muted">Loading order items...</p>
                                </div>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>

        </c:if>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/showDetailOrderAdmin.js">

</script>



<%@ include file="adminFooter.jsp" %>
