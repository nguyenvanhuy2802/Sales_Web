<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/homeAdmin">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Quản lý người dùng</li>
    </ol>
</nav>

<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4 p-3 bg-light rounded shadow-sm">
      <h1 class="fw-bold text-primary mb-0">Quản Lý Người Dùng</h1>
  </div>


    <!-- Search and Add User -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="input-group w-50">
            <span class="input-group-text"><i class="bi bi-search"></i></span>
            <input type="text" class="form-control" id="searchUser" placeholder="Tìm kiếm người dùng">
        </div>
        <button class="btn btn-primary" onclick="addUser()">
            <i class="bi bi-person-plus"></i> Thêm Người Dùng
        </button>
    </div>
<div class="table-responsive mt-4">
    <c:if test="${empty userList}">
        <div class="alert alert-warning text-center" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> Không có người dùng!
        </div>
    </c:if>
    <c:if test="${not empty userList}">
        <table class="table table-hover table-bordered align-middle">
            <thead class="table-dark">
                <tr class="text-center">
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Address</th>
                    <th scope="col">Username</th>
                    <th scope="col">Password</th>
                    <th scope="col">Role</th>
                    <th scope="col">Image</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody id="userTableBody">
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td class="text-center">${user.userId}</td>
                        <td>
                            <input type="text" value="${user.name}" id="name_${user.userId}" class="form-control">
                        </td>
                        <td>
                            <input type="text" value="${user.email}" id="email_${user.userId}" class="form-control">
                        </td>
                        <td>
                            <input type="text" value="${user.phone}" id="phone_${user.userId}" class="form-control">
                        </td>
                        <td>
                            <input type="text" value="${user.address}" id="address_${user.userId}" class="form-control">
                        </td>
                        <td>
                            <input type="text" value="${user.username}" id="username_${user.userId}" class="form-control">
                        </td>
                        <td>
                            <input type="password" value="${user.password}" id="password_${user.userId}" class="form-control">
                        </td>
                        <td>
                            <select id="role_${user.userId}" class="form-select">
                                <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>Admin</option>
                                <option value="customer" ${user.role == 'customer' ? 'selected' : ''}>Customer</option>
                            </select>
                        </td>
                        <td class="text-center">
                            <img id="profileImage_${user.userId}"
                                 src="${pageContext.request.contextPath}${user.profileImage}"
                                 alt="User Image"
                                 class="rounded-circle shadow-sm"
                                 style="width: 50px; height: 50px; object-fit: cover;">
                        </td>
                        <td>
                            <div class="d-flex justify-content-center gap-2">
                                <button class="btn btn-success btn-sm" onclick="saveUser(${user.userId})">
                                    <i class="bi bi-check-circle"></i>
                                </button>
                                <button class="btn btn-danger btn-sm" onclick="deleteUser(${user.userId})">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>



</div>

<script>
    // Add User Function
    function addUser() {
        alert('Thêm người dùng mới!');
        // Logic to show a form for adding new users
    }

    // Edit User Function
    function saveUser(id) {
        alert(`Lưu người dùng thành công`);
        // Logic to save user details
    }

    // Delete User Function
    function deleteUser(id) {
        if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
            alert(`Đã xóa người dùng`);
            // Logic to delete the user
        }
    }
</script>

<%@ include file="adminFooter.jsp" %>
