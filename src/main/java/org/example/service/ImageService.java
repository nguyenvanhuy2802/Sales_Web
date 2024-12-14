package org.example.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public class ImageService {
    private static final String UPLOAD_DIRECTORY = "images/avatars";

    public static String uploadImage(HttpServletRequest request, int index) {
        String uploadPath = request.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;

        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // Kiểm tra nếu yêu cầu chứa dữ liệu tải lên tệp
            if (ServletFileUpload.isMultipartContent(request)) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Phân tích yêu cầu
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    // Kiểm tra xem đây là trường form hay tệp được tải lên
                    if (!item.isFormField() && item.getName() != null && !item.getName().isEmpty()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File uploadedFile = new File(filePath);

                        // Lưu tệp
                        item.write(uploadedFile);
                        return "/" + UPLOAD_DIRECTORY + "/" + fileName; // Trả về đường dẫn tương đối
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null; // Trả về null nếu upload thất bại
    }
}
