$(document).ready(function() {
    const contextPath = $('meta[name="context-path"]').attr('content');

  $(document).on('click', '.view-details-btn', async function() {
      const button = $(this);
      const orderId = button.data('order-id');
      const detailsRow = $('#orderDetails' + orderId); // Lấy phần tử chi tiết
      const container = detailsRow.find('.order-items-container'); // Lấy container chứa sản phẩm

      // Toggle button text based on the current state
      if (detailsRow.hasClass('show')) {
          button.html('<i class="bi bi-eye"></i> Xem Chi Tiết');
      } else {
          button.html('<i class="bi bi-eye-slash"></i> Ẩn Chi Tiết');
      }

      // Toggle the collapse state of the details row with a CSS transition
      detailsRow.toggleClass('show');

      // Nếu chưa tải chi tiết, thì thực hiện việc gọi API
      if (!detailsRow.data('loaded')) {
          try {
              const response = await fetch(contextPath + '/order-items?orderId=' + orderId);
              const data = await response.json();

              if (data.length === 0) {
                  container.html('<p>No items found for this order.</p>');
                  return;
              }

              // Tạo bảng để hiển thị chi tiết sản phẩm
              const table = $('<table class="table table-bordered mt-3"></table>');
              const thead = $('<thead class="table-light"><tr><th>Ảnh Sản Phẩm</th><th>Sản Phẩm</th><th>Số Lượng</th><th>Giá</th></tr></thead>');
              const tbody = $('<tbody></tbody>');

              data.forEach(item => {
                  const row = $('<tr></tr>');
                  const productImageUrl = contextPath + item.productImage;
                  const imgTag = `<img src="${productImageUrl}" alt="${item.productName}" class="img-thumbnail" style="width: 100px; height: auto;" />`;

                  row.append(`<td>${imgTag}</td>`);
                  row.append(`<td>${item.productName}</td>`);
                  row.append(`<td>${item.quantity}</td>`);
                  row.append(`<td>${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.price)}</td>`);
                  tbody.append(row);
              });

              table.append(thead).append(tbody);
              container.html(table); // Thêm bảng vào phần tử container
              detailsRow.data('loaded', true); // Đánh dấu đã tải chi tiết

          } catch (error) {
              container.html('<p class="text-danger">Failed to load order details. Please try again later.</p>');
          }
      }
  });



 $(document).on('click', '.cancel-order-btn', function() {
     var button = $(this);
     var orderId = button.data('order-id');

     if (confirm("Bạn có chắc chắn muốn hủy đơn hàng này không?")) {
         $.ajax({
             url: contextPath + '/cancel-order',
             method: 'POST',
             data: { orderId: orderId },
             success: function(response) {
                 if (response.status === "success") {
                     // Get the cancelled order row
                     var orderRow = button.closest('tr');
                     var orderData = orderRow.children('td').map(function() {
                         return $(this).text();
                     }).get();

                     // Tạo dòng mới cho đơn hàng đã hủy
                     var cancelledOrderRow = `
                         <tr>
                             <td>${orderData[0]}</td>
                             <td>${orderData[1]}</td>
                             <td>Cancelled</td>
                             <td>${orderData[3]}</td>
                             <td>
                                 <button class="btn btn-primary btn-sm view-details-btn" data-order-id="${orderData[0]}">
                                     <i class="bi bi-eye"></i> Xem Chi Tiết
                                 </button>
                             </td>
                         </tr>
                         <tr id="orderDetails${orderData[0]}" class="collapse">
                             <td colspan="5">
                                 <div class="order-items-container"></div>
                             </td>
                         </tr>
                     `;

                     // Thêm vào tab đã hủy
                     $('#cancelledOrders tbody').append(cancelledOrderRow);
                     // Xóa dòng đơn hàng từ bảng đang chờ
                     orderRow.remove();
                     location.reload();

                 } else {
                     alert(response.message);
                 }
             },
             error: function(xhr, status, error) {
                 alert("Có lỗi xảy ra khi hủy đơn hàng. Vui lòng thử lại.");
             }
         });
     }
 });


});
