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
            <button class="btn btn-primary" onclick="createKey()">Tạo key</button>
            <button class="btn btn-secondary" onclick="uploadKey()">Upload key</button>
            <button class="btn btn-info text-white" onclick="generateReport()">Report</button>
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
                            <input type="text" class="form-control" id="publicKey"
                                   value="${publicKey.name}" readonly>
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

function downloadKey(keyContent, fileName) {
    // Create a Blob object containing the key content
    const blob = new Blob([keyContent], { type: "text/plain" });

    // Create a temporary <a> element for downloading the key
    const anchor = document.createElement('a');
    anchor.href = URL.createObjectURL(blob);
    anchor.download = fileName;
    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);

    // Clean up the object URL
    URL.revokeObjectURL(anchor.href);
}

    function uploadKey() {
        alert('Upload key logic here!');
    }

    function generateReport() {
        alert('Generate report logic here!');
    }

    function copyToClipboard() {
        const publicKeyInput = document.getElementById('publicKey');
        publicKeyInput.select();
        publicKeyInput.setSelectionRange(0, 99999); // For mobile devices
        navigator.clipboard.writeText(publicKeyInput.value).then(() => {
            alert('Public key copied to clipboard!');
        }).catch(err => {
            console.error('Failed to copy text: ', err);
        });
    }
</script>

<%@ include file="footer.jsp" %>
