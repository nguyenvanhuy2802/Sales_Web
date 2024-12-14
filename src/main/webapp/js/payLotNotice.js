document.addEventListener('DOMContentLoaded', function() {
    const quantities = document.querySelectorAll('input[name="quantities"]');
    const grandTotalElement = document.querySelector('h4 strong');
    const grandTotalHidden = document.getElementById('grandTotalHidden');

    quantities.forEach(function(quantityInput) {
        quantityInput.addEventListener('change', function() {
            updateTotal();
        });
    });

    function updateTotal() {
        let grandTotal = 0;
        quantities.forEach(function(quantityInput) {
            const row = quantityInput.closest('tr');
            const unitPrice = parseFloat(row.querySelector('input[name="unitPrices"]').value);
            const quantity = parseInt(quantityInput.value);
            const totalAmount = unitPrice * quantity;

            row.querySelector('input[name="totalAmounts"]').value = totalAmount;
            row.querySelector('.totalPrice').innerHTML = new Intl.NumberFormat('vi-VN').format(totalAmount) + ' VNĐ';
            grandTotal += totalAmount;
        });

        // Cập nhật tổng cộng hiển thị và input hidden
        grandTotalElement.textContent = new Intl.NumberFormat('vi-VN').format(grandTotal) + ' VNĐ';
        grandTotalHidden.value = grandTotal; // Cập nhật giá trị input hidden
    }
});
