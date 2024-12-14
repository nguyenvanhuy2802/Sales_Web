document.addEventListener("DOMContentLoaded", function () {
    const toggleButtons = document.querySelectorAll('.toggle-details');
    const contextPath = $('meta[name="context-path"]').attr('content'); // Đảm bảo context-path đúng

    toggleButtons.forEach(button => {
        button.addEventListener('click', function () {
            const parentRow = this.closest('tr');
            const detailsRow = parentRow.nextElementSibling;
            const orderId = parentRow.querySelector('td').textContent.trim();
            console.log("Fetching order items for ID:", orderId);
            const orderItemsContainer = detailsRow.querySelector('.order-items-container');

            // Toggle visibility
            if (detailsRow.style.display === 'none' || !detailsRow.style.display) {
                detailsRow.style.display = 'table-row';

                // Check if data is already loaded
                if (!detailsRow.dataset.loaded) {
                    // Fetch order items via AJAX
                    fetch(contextPath + '/order-items?orderId=' + orderId)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`HTTP error! status: ${response.status}`);
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data && data.length > 0) {
                                // Create table
                                const table = document.createElement('table');
                                table.classList.add('table', 'table-bordered', 'mt-3');

                                // Create table header
                                const thead = `
                                    <thead class="table-light">
                                        <tr>
                                            <th>Ảnh Sản Phẩm</th>
                                            <th>Sản Phẩm</th>
                                            <th>Số Lượng</th>
                                            <th>Đơn Giá</th>
                                        </tr>
                                    </thead>`;
                                table.innerHTML = thead;

                                // Create table body
                                const tbody = document.createElement('tbody');
                                data.forEach(item => {
                                    const productImageUrl = contextPath + item.productImage;
                                    const row = `
                                        <tr>
                                            <td>
                                                <img src="${productImageUrl}" alt="${item.productName}" class="img-thumbnail" style="width: 100px; height: auto;" />
                                            </td>
                                            <td>${item.productName}</td>
                                            <td>${item.quantity}</td>
                                            <td>${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.price)}</td>
                                        </tr>`;
                                    tbody.innerHTML += row;
                                });
                                table.appendChild(tbody);

                                // Add table to container
                                orderItemsContainer.innerHTML = '';
                                orderItemsContainer.appendChild(table);
                            } else {
                                // No items found
                                orderItemsContainer.innerHTML = '<p class="text-muted">Không tìm thấy sản phẩm nào trong đơn hàng này.</p>';
                            }

                            // Mark as loaded
                            detailsRow.dataset.loaded = true;
                        })
                        .catch(error => {
                            console.error('Error fetching order items:', error);
                            orderItemsContainer.innerHTML = '<p class="text-danger">Đã xảy ra lỗi khi tải sản phẩm. Vui lòng thử lại sau.</p>';
                        });
                }
            } else {
                detailsRow.style.display = 'none';
            }
        });
    });

 const updateButtons = document.querySelectorAll(".update-status");
 updateButtons.forEach(button => {
         button.addEventListener("click", function () {
             // Lấy thông tin orderId từ nút
             const orderId = this.getAttribute("data-order-id");

             // Lấy trạng thái mới từ dropdown
             const parentRow = this.closest("tr");
             const statusSelect = parentRow.querySelector(".order-status");
             const newStatus = statusSelect.value;

             // Gửi yêu cầu cập nhật trạng thái đến máy chủ
             fetch(contextPath + '/update-order-status', {
                 method: "POST",
                 headers: {
                     "Content-Type": "application/json",
                 },
                 body: JSON.stringify({ orderId: orderId, status: newStatus }),
             })
                 .then(response => {
                     if (!response.ok) {
                         throw new Error("Failed to update order status.");
                     }
                     return response.json();
                 })
                 .then(data => {
                     // Thông báo thành công
                     if (data.success) {
                         alert("Updated successfully!");
                     } else {
                         alert("Failed to update order status.");
                     }
                 })
                 .catch(error => {
                     console.error("Error updating order status:", error);
                     alert("An error occurred while updating the status. Please try again.");
                 });
         });
     });

});
