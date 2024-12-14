document.addEventListener('DOMContentLoaded', function () {
    const decreaseBtn = document.getElementById('button-decrease');
    const increaseBtn = document.getElementById('button-increase');
    const quantityInput = document.getElementById('quantity');
    const unitPriceElement = document.getElementById('unitPrice');
    const totalPriceElement = document.getElementById('totalPrice');
    const hiddenQuantityInput = document.getElementById('hiddenQuantity');
    const hiddenTotalAmountInput = document.getElementById('hiddenTotalAmount');

    function updateTotalPriceAndHiddenQuantity() {
        let quantity = parseInt(quantityInput.value);
        if (isNaN(quantity) || quantity < 1) {
            quantity = 1;
            quantityInput.value = quantity;
        }

        const unitPrice = parseInt(unitPriceElement.dataset.unitPrice, 10);
        const total = unitPrice * quantity;

        // Cập nhật giá trị tổng cộng
        totalPriceElement.textContent = `${new Intl.NumberFormat('vi-VN').format(total)} VND`;

        // Đồng bộ giá trị với trường ẩn
        hiddenQuantityInput.value = quantity;
        hiddenTotalAmountInput.value = total;
    }

    decreaseBtn.addEventListener('click', function () {
        let currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
            updateTotalPriceAndHiddenQuantity();
        }
    });

    increaseBtn.addEventListener('click', function () {
        let currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
        updateTotalPriceAndHiddenQuantity();
    });

    quantityInput.addEventListener('input', function () {
        let value = parseInt(quantityInput.value);
        if (isNaN(value) || value < 1) {
            quantityInput.value = 1;
        }
        updateTotalPriceAndHiddenQuantity();
    });

    // Cập nhật ngay khi tải trang
    updateTotalPriceAndHiddenQuantity();
});
