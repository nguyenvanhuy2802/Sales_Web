<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb shadow-sm bg-light p-3 rounded">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/product">Trang chủ</a></li>
        <li class="breadcrumb-item active" aria-current="page">Quản lý key</li>
    </ol>
</nav>

<div class="content-wrapper">
    <div class="container mt-4">
        <h2 class="mb-4">Quản lý key</h2>

        <!-- Buttons Section -->
        <div class="d-flex mb-4 gap-3">
            <button class="btn btn-primary" onclick="promptKeyCreation()">Tạo key</button>
            <button class="btn btn-secondary" onclick="openUploadKeyModal()">Upload key</button>
            <button class="btn btn-info text-white" onclick="promptReport()">Report</button>
        </div>


<div id="timestampModal" style="display: none;">
    <label for="timestamp">Select Timestamp:</label>
    <input type="datetime-local" id="timestamp" name="timestamp" required>
    <button onclick="submitReport()">Submit</button>
    <button onclick="closeModal()">Cancel</button>
</div>

       <!-- Public Key Display Section -->
       <div class="card shadow-sm">
           <div class="card-header bg-primary text-white">
               <h5 class="mb-0">Public Key</h5>
           </div>
           <div class="card-body">
               <div class="input-group">
                   <c:choose>
                       <c:when test="${not empty publicKey}">
                           <!-- Allow editing the input if needed (remove readonly for editing) -->
                           <input type="text" class="form-control" id="publicKey" value="${publicKey.name}" ${not empty publicKey ? '' : 'readonly'}>
                       </c:when>
                       <c:otherwise>
                           <input type="text" class="form-control" id="publicKey" readonly>
                       </c:otherwise>
                   </c:choose>
                   <button class="btn btn-outline-secondary" onclick="copyToClipboard()">Copy</button>
               </div>
           </div>
       </div>
    </div>
</div>


<!-- Upload Key Modal -->
<div class="modal fade" id="uploadKeyModal" tabindex="-1" aria-labelledby="uploadKeyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="uploadKeyModalLabel">Upload Public Key</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
           <div class="modal-body">
               <form id="uploadKeyForm">
                   <div class="mb-3">
                       <label for="newPublicKey" class="form-label">Public Key (Nhập trực tiếp)</label>
                       <textarea class="form-control" id="newPublicKey" rows="3" placeholder="Nhập public key tại đây"></textarea>
                   </div>
                   <div class="mb-3">
                       <label for="uploadFile" class="form-label">Hoặc Upload Public Key (File)</label>
                       <input type="file" class="form-control" id="uploadFile" accept=".txt" onchange="switchToFileUpload()">
                   </div>
                   <button type="button" class="btn btn-secondary" id="cancelFileSelect" onclick="cancelFileSelection()">Hủy chọn file</button>
               </form>
           </div>


            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" onclick="uploadKey()">Upload</button>
            </div>
        </div>
    </div>
</div>

    <!-- Toast Notification -->
     <div class="toast-container position-fixed bottom-0 end-0 p-3">
         <div id="successToast" class="toast bg-success text-white align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
             <div class="d-flex">
                 <div class="toast-body" id="toastMessage"></div>
                 <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
             </div>
         </div>
     </div>

<script>
    function createKey() {
        fetch('${pageContext.request.contextPath}/generateKey')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const publicKeyInput = document.getElementById('publicKey');
                publicKeyInput.value = data.publicKey;

                // Download the private key
                downloadKey(data.privateKey, "private_key.txt");

                // Download the public key
                downloadKey(data.publicKey, "public_key.txt");
                alert('Key generated successfully!');
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                alert('Failed to generate key!');
            });
    }

    function promptKeyCreation() {
        const userConfirmation = confirm("Bạn có muốn tạo bộ key mới không?");
        if (userConfirmation) {
            createKey();
        }
    }

    function downloadKey(keyContent, fileName) {
        const blob = new Blob([keyContent], { type: "text/plain" });
        const anchor = document.createElement('a');
        anchor.href = URL.createObjectURL(blob);
        anchor.download = fileName;
        document.body.appendChild(anchor);
        anchor.click();
        document.body.removeChild(anchor);
        URL.revokeObjectURL(anchor.href);
    }

    function openUploadKeyModal() {
        const modal = new bootstrap.Modal(document.getElementById('uploadKeyModal'));
        modal.show();
    }

function switchToFileUpload() {
    const fileInput = document.getElementById('uploadFile');
    const directInput = document.getElementById('newPublicKey');
    const cancelBtn = document.getElementById('cancelFileSelect');

    // Nếu có file chọn, xóa nội dung trong textarea và tắt chế độ nhập
    if (fileInput.files.length > 0) {
        directInput.value = '';
        directInput.setAttribute('readonly', 'true');  // Làm cho textarea không thể chỉnh sửa
        cancelBtn.style.display = 'inline-block';
    } else {
        cancelBtn.style.display = 'none';
        directInput.removeAttribute('readonly');
    }
}

function cancelFileSelection() {
    const fileInput = document.getElementById('uploadFile');
    const directInput = document.getElementById('newPublicKey');
    const cancelBtn = document.getElementById('cancelFileSelect');

    // Hủy chọn file và xóa nội dung trong textarea
    fileInput.value = '';
    directInput.value = '';
    directInput.removeAttribute('readonly');
    cancelBtn.style.display = 'none';
}


function uploadKey() {
    // Kiểm tra nếu có nhập trực tiếp public key
    const publicKeyInput = document.getElementById('newPublicKey').value.trim();
    const fileInput = document.getElementById('uploadFile');
    let publicKey = publicKeyInput;

    // Nếu không có public key trực tiếp, đọc từ file
    if (!publicKey && fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const reader = new FileReader();

        reader.onload = function(event) {
            publicKey = event.target.result;

            if (!publicKey || publicKey.trim().length === 0) {
                alert('File không hợp lệ hoặc không chứa public key!');
                return;
            }

            sendKeyToServer(publicKey); // Gửi key đã đọc từ file hoặc nhập trực tiếp
        };

        reader.onerror = function() {
            alert('Có lỗi xảy ra khi đọc file!');
        };

        reader.readAsText(file);
    } else if (publicKey) {
        sendKeyToServer(publicKey); // Nếu có nhập public key trực tiếp, gửi lên server
    } else {
        alert('Vui lòng nhập hoặc tải lên public key!');
    }
}


// Hàm gửi public key lên server
function sendKeyToServer(publicKey) {
    // Gửi yêu cầu POST đến server với public key

    fetch('${pageContext.request.contextPath}/uploadKey', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ publicKey: publicKey }) // Chuyển đổi public key thành chuỗi JSON để gửi
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to upload key');
        }
        return response.json();
    })
    .then(data => {
        if (data.publicKey) {
            const publicKeyDisplay = document.getElementById('publicKey');
            publicKeyDisplay.value = publicKey;
            publicKeyDisplay.removeAttribute('readonly');
            alert('Key uploaded successfully!');
            const modal = bootstrap.Modal.getInstance(document.getElementById('uploadKeyModal'));
            modal.hide();
        } else {
            alert('Failed to upload key: ' + (data.message || 'Unknown error'));
        }
    })
    .catch(error => {
        console.error('Error uploading key:', error);
        alert('Có lỗi xảy ra khi upload key!');
    });
}


  function promptReport() {
      const publicKeyInput = document.getElementById('publicKey');

      // Kiểm tra giá trị của input
      if (!publicKeyInput || publicKeyInput.value.trim() == '') {
          alert("Bạn chưa tạo bộ key!");
          return;
      }

      // Hiển thị hộp thoại chọn timestamp
      document.getElementById('timestampModal').style.display = 'block';
  }

  function closeModal() {
      document.getElementById('timestampModal').style.display = 'none';
  }

function submitReport() {
    const timestampInput = document.getElementById('timestamp');

    // Kiểm tra xem người dùng đã chọn thời gian chưa
    if (!timestampInput || timestampInput.value.trim() == '') {
        alert("Bạn chưa chọn thời gian khi key bị lộ!");
        return;
    }

    let exposureTimestamp = timestampInput.value;  // Thời gian người dùng chọn

    // Nếu người dùng chọn định dạng không phải ISO 8601, chuyển đổi thành định dạng đúng
    // Ví dụ: nếu người dùng chọn ngày tháng năm, chúng ta cần định dạng lại.
    const date = new Date(exposureTimestamp);
    if (isNaN(date.getTime())) {
        alert("Định dạng thời gian không hợp lệ.");
        return;
    }


    exposureTimestamp = date.toISOString();

    // Gửi thông tin lên server
    fetch('${pageContext.request.contextPath}/reportKey', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            exposureTimestamp: exposureTimestamp
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        alert('Report successfully!');
        closeModal();  // Đóng hộp thoại
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
        alert('Failed to report!');
    });
}




function copyToClipboard() {
    const publicKeyInput = document.getElementById('publicKey');
    publicKeyInput.select();
    publicKeyInput.setSelectionRange(0, 99999); // For mobile devices

    navigator.clipboard.writeText(publicKeyInput.value).then(() => {
        showToast("Public key copied to clipboard!");
    }).catch(err => {
        console.error('Failed to copy text: ', err);
    });
}

function showToast(message) {
    const toastMessage = document.getElementById('toastMessage');
    toastMessage.textContent = message;

const successToast = new bootstrap.Toast(document.getElementById('successToast'), {
        delay: 800 // Time in milliseconds before the toast disappears
    });
    successToast.show();
}

</script>

<%@ include file="footer.jsp" %>
