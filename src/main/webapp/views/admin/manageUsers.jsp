<%@ include file="adminHeader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-4">
    <h2>Quản Lý Người Dùng</h2>
    <table class="table table-bordered table-hover mt-4">
        <thead>
            <tr>
                <th>#</th>
                <th>Tên Người Dùng</th>
                <th>Email</th>
                <th>Trạng Thái</th>
                <th>Hành Động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}" varStatus="status">
                <tr>
                    <th>${status.count}</th>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.active ? 'Hoạt Động' : 'Bị Khóa'}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/manageUsers" method="post" class="d-inline">
                            <input type="hidden" name="userId" value="${user.userId}">
                            <input type="hidden" name="action" value="${user.active ? 'deactivateUser' : 'activateUser'}">
                            <button type="submit" class="btn btn-${user.active ? 'warning' : 'success'} btn-sm">
                                ${user.active ? 'Khóa' : 'Kích Hoạt'}
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/admin/manageUsers" method="post" class="d-inline" onsubmit="return confirm('Bạn có chắc chắn muốn xóa người dùng này?');">
                            <input type="hidden" name="userId" value="${user.userId}">
                            <input type="hidden" name="action" value="deleteUser">
                            <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="adminFooter.jsp" %>
