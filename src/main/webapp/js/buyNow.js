<!-- Thêm JavaScript để cập nhật tổng giá tiền và gửi dữ liệu cho Buy Now -->
document.addEventListener('DOMContentLoaded', function() {
    const totalPriceElement = document.getElementById('totalPrice');
    if (!totalPriceElement) {
        return;
    }

    const checkboxes = document.querySelectorAll('.cartItem-checkbox');
    const buyNowForm = document.getElementById('buyNowForm');
    // Hàm định dạng số theo định dạng Việt Nam
    function formatNumber(num) {
        return new Intl.NumberFormat('vi-VN').format(num) + ' VNĐ';
    }

    // Hàm cập nhật tổng giá tiền
    function updateTotal() {
        let total = 0;
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                const tr = checkbox.closest('tr');
                const price = parseFloat(tr.getAttribute('data-price'));
                const quantity = parseInt(tr.getAttribute('data-quantity'));
                total += price * quantity;
            }
        });
        totalPriceElement.textContent = formatNumber(total);
    }

    // Thêm sự kiện lắng nghe cho mỗi checkbox
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', updateTotal);
    });

    // Khởi tạo tổng giá tiền khi tải trang
    updateTotal();

    // Khi submit form Buy Now, đảm bảo chỉ gửi các sản phẩm được chọn
    buyNowForm.addEventListener('submit', function(event) {
        // Không cần thêm gì, vì chỉ các checkbox liên kết với form này sẽ được gửi
        // Nhưng nếu cần, có thể kiểm tra xem ít nhất một sản phẩm đã được chọn
        let anyChecked = false;
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                anyChecked = true;
            }
        });
        if (!anyChecked) {
            event.preventDefault();
            alert('Vui lòng chọn ít nhất một sản phẩm để tiến hành thanh toán.');
        }
    });
});