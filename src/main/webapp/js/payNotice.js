document.addEventListener('DOMContentLoaded', function () {
    const decreaseBtn = document.getElementById('button-decrease');
    const increaseBtn = document.getElementById('button-increase');
    const quantityInput = document.getElementById('quantity');
    const unitPriceElement = document.getElementById('unitPrice');
    const totalPriceElement = document.getElementById('totalPrice');

    function updateTotalPrice() {
        let quantity = parseInt(quantityInput.value);
        if (isNaN(quantity) || quantity < 1) {
            quantity = 1;
            quantityInput.value = quantity;
        }

        const unitPrice = parseInt(unitPriceElement.dataset.unitPrice, 10);
        const total = unitPrice * quantity;

        // Format total without currency symbol and add "VND" suffix
        totalPriceElement.textContent = `${new Intl.NumberFormat('vi-VN').format(total)} VND`;
    }

    decreaseBtn.addEventListener('click', function () {
        let currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
            updateTotalPrice();
        }
    });

    increaseBtn.addEventListener('click', function () {
        let currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
        updateTotalPrice();
    });

    quantityInput.addEventListener('input', function () {
        let value = parseInt(quantityInput.value);
        if (isNaN(value) || value < 1) {
            quantityInput.value = 1;
        }
        updateTotalPrice();
    });

    updateTotalPrice();
});
