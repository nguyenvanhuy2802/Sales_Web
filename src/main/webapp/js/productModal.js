document.addEventListener('DOMContentLoaded', function () {
    var actionModal = document.getElementById('actionModal');
    var contextPathMeta = document.querySelector('meta[name="context-path"]');
    var contextPath = contextPathMeta ? contextPathMeta.getAttribute('content') : '';

    if (!actionModal) {
        console.error("Không tìm thấy modal với ID 'actionModal'");
        return;
    }

    actionModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget; // Button đã kích hoạt modal
        var action = button.getAttribute('data-action'); // Lấy hành động: 'buyNow' hoặc 'addToCart'
        var productId = button.getAttribute('data-id');
        var productName = button.getAttribute('data-name');
        var productPrice = button.getAttribute('data-price');
        var productImage = button.getAttribute('data-image');

        var modalTitle = actionModal.querySelector('#actionModalLabel');
        var modalProductImage = actionModal.querySelector('#modalProductImage');
        var modalProductName = actionModal.querySelector('#modalProductName');
        var modalProductPrice = actionModal.querySelector('#modalProductPrice');
        var modalProductId = actionModal.querySelector('#modalProductId');
        var quantityInput = actionModal.querySelector('#modalQuantity');
        var modalSubmitButton = actionModal.querySelector('#modalSubmitButton');
        var actionForm = actionModal.querySelector('#actionForm');
        var modalAction = actionModal.querySelector('#modalAction');

        // Gán đường dẫn ảnh đầy đủ
        modalProductImage.src = contextPath + productImage;
        modalProductName.textContent = productName;
        modalProductPrice.textContent = 'Giá: ' + productPrice + ' VND';
        modalProductId.value = productId;
        quantityInput.value = 1;
        quantityInput.setAttribute('min', '1');

        if(action === 'buyNow') {
            modalTitle.textContent = 'Xác nhận Mua Ngay';
            modalSubmitButton.textContent = 'Xác nhận Mua Ngay';
            modalSubmitButton.classList.remove('btn-primary');
            modalSubmitButton.classList.add('btn-success');
            actionForm.action = contextPath + '/buyNow';
            modalAction.value = 'buyNow';
        } else if(action === 'addToCart') {
            modalTitle.textContent = 'Thêm vào Giỏ Hàng';
            modalSubmitButton.textContent = 'Thêm vào Giỏ';
            modalSubmitButton.classList.remove('btn-success');
            modalSubmitButton.classList.add('btn-primary');
            actionForm.action = contextPath + '/addToCart';
            modalAction.value = 'addToCart';
        }
    });

    // Xử lý các nút tăng và giảm số lượng
    actionModal.addEventListener('click', function (event) {
        if (event.target.id === 'decreaseQuantity') {
            var quantityInput = actionModal.querySelector('#modalQuantity');
            var currentValue = parseInt(quantityInput.value, 10);
            var minValue = parseInt(quantityInput.min, 10);
            if (currentValue > minValue) {
                quantityInput.value = currentValue - 1;
            }
        } else if (event.target.id === 'increaseQuantity') {
            var quantityInput = actionModal.querySelector('#modalQuantity');
            var currentValue = parseInt(quantityInput.value, 10);
            quantityInput.value = currentValue + 1;
        }
    });
});
