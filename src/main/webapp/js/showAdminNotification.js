const contextPath = $('meta[name="context-path"]').attr('content');
$(document).ready(function () {
    // Xử lý khi bấm vào biểu tượng chuông
    $(".notification-icon").on("click", function () {
        // Gửi AJAX request đến Servlet để lấy dữ liệu thông báo
        $.ajax({
            url: contextPath + "/notifications", // URL của Servlet
            type: "GET",
            dataType: "json", // Dữ liệu trả về ở định dạng JSON
            success: function (orderResults) { // Nhận response là orderResults
                // Xóa danh sách cũ
                $("#notificationList").empty();

                // Kiểm tra nếu orderResults null hoặc không có phần tử nào
                if (!orderResults || Object.keys(orderResults).length === 0) {
                    $("#notificationList").append(`
                        <li class="list-group-item text-center text-muted">
                            Không có thông báo đơn hàng nào.
                        </li>
                    `);
                } else {
                    // Duyệt qua các phần tử trong orderResults (là Map)
                    for (const [orderId, isVerified] of Object.entries(orderResults)) {
                        let verifyStatus = "";
                        let badgeClass = "";
                        let icon = "";
                        let actionButton = "";

                        // Xử lý thông báo theo trạng thái xác minh của đơn hàng
                        if (isVerified === true) {
                            verifyStatus = "hợp lệ";
                            badgeClass = "bg-success text-white"; // Xanh lá
                            icon = `<i class="fas fa-check-circle text-white"></i>`; // Icon hợp lệ
                        } else if (isVerified === false) {
                            verifyStatus = "đã bị chỉnh sửa";
                            badgeClass = "bg-danger text-white"; // Đỏ
                            icon = `<i class="fas fa-exclamation-circle text-white"></i>`; // Icon cảnh báo
                            actionButton = `<button class="btn btn-danger btn-sm" style="background-color: #b30000; color: white; border-color: #990000;" onclick="cancelOrder(${orderId})">Hủy đơn hàng</button>`;
                        } else {
                            verifyStatus = "chưa được xác thực";
                            badgeClass = "bg-warning text-dark"; // Vàng
                            icon = `<i class="fas fa-exclamation-triangle text-black"></i>`; // Tam giác cảnh báo
                        }

                        // Tạo phần tử danh sách cho từng đơn hàng
                        const listItem = `
                            <li class="list-group-item d-flex justify-content-between align-items-center ${badgeClass}" style="border-radius: 10px; margin-bottom: 10px;">
                                <span class="d-flex align-items-center gap-2">
                                    ${icon}
                                    <span>Đơn hàng: ${orderId} ${verifyStatus}</span>
                                </span>
                                ${actionButton}
                            </li>`;

                        // Thêm phần tử vào danh sách thông báo
                        $("#notificationList").append(listItem);
                    }
                }

                // Hiển thị modal
                $("#notificationModal").modal("show");
            },
            error: function (error) {
                console.error("Có lỗi xảy ra khi lấy dữ liệu", error);
            }
        });
    });
});

function cancelOrder(orderId) {
    if (confirm("Bạn có chắc chắn muốn hủy đơn hàng này?")) {
        // Gửi yêu cầu hủy đơn hàng (gửi AJAX đến servlet hủy đơn hàng)
        $.ajax({
            url: contextPath + "/cancel-order",
            type: "POST",
            data: { orderId: orderId },
            dataType: "json",
            success: function (response) {
                // Kiểm tra trạng thái trả về từ servlet
                if (response.status === "success") {
                    alert(response.message);
                    location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function (error) {
                console.error("Có lỗi xảy ra khi hủy đơn hàng", error);
                alert("Có lỗi xảy ra khi hủy đơn hàng. Vui lòng thử lại.");
            }
        });
    }
}
