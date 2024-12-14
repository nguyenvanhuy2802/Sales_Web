<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="content-wrapper">
    <div class="container mt-5">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3">

                <div class="list-group shadow-sm">
                    </a>
                    <a href="${pageContext.request.contextPath}/userManagement" class="list-group-item list-group-item-action d-flex align-items-center">
                        <i class="bi bi-person-circle me-2"></i> Quản Lý Người Dùng
                    </a>
                    <a href="${pageContext.request.contextPath}/categoryManagement" class="list-group-item list-group-item-action d-flex align-items-center">
                        <i class="bi bi-list-task me-2"></i> Quản Lý Danh Mục
                    </a>
                    <a href="${pageContext.request.contextPath}/productManagement" class="list-group-item list-group-item-action d-flex align-items-center">
                        <i class="bi bi-box-seam me-2"></i> Quản Lý Sản Phẩm
                    </a>
                    <a href="${pageContext.request.contextPath}/orderManagement" class="list-group-item list-group-item-action d-flex align-items-center">
                        <i class="bi bi-bag-check me-2"></i> Quản Lý Đơn Hàng
                    </a>
                </div>
            </div>

            <div class="col-md-9">

                       <div class="row">
                           <!-- User Card -->
                           <div class="col-md-3 mb-4">
                               <div class="card shadow-sm border-primary text-center">
                                   <div class="card-body">
                                       <h5 class="card-title text-primary">Người Dùng</h5>
                                       <p class="card-text display-4">${userList.size()}</p>
                                       <a href="${pageContext.request.contextPath}/userManagement" class="btn btn-primary">
                                           <i class="bi bi-person-circle me-2"></i> Quản Lý
                                       </a>
                                   </div>
                               </div>
                           </div>

                           <!-- Category Card -->
                           <div class="col-md-3 mb-4">
                               <div class="card shadow-sm border-success text-center">
                                   <div class="card-body">
                                       <h5 class="card-title text-success">Danh Mục</h5>
                                       <p class="card-text display-4">${categoryList.size()}</p>
                                       <a href="${pageContext.request.contextPath}/categoryManagement" class="btn btn-success">
                                           <i class="bi bi-list-task me-2"></i> Quản Lý
                                       </a>
                                   </div>
                               </div>
                           </div>

                           <!-- Product Card -->
                           <div class="col-md-3 mb-4">
                               <div class="card shadow-sm border-warning text-center">
                                   <div class="card-body">
                                       <h5 class="card-title text-warning">Sản Phẩm</h5>
                                       <p class="card-text display-4">${productList.size()}</p>
                                       <a href="${pageContext.request.contextPath}/productManagement" class="btn btn-warning">
                                           <i class="bi bi-box-seam me-2"></i> Quản Lý
                                       </a>
                                   </div>
                               </div>
                           </div>

                           <!-- Order Card -->
                           <div class="col-md-3 mb-4">
                               <div class="card shadow-sm border-danger text-center">
                                   <div class="card-body">
                                       <h5 class="card-title text-danger">Đơn Hàng</h5>
                                       <p class="card-text display-4">${orderList.size()}</p>
                                       <a href="${pageContext.request.contextPath}/orderManagement" class="btn btn-danger">
                                           <i class="bi bi-bag-check me-2"></i> Quản Lý
                                       </a>
                                   </div>
                               </div>
                           </div>
                       </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="adminFooter.jsp" %>