package org.example.servlet;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.example.DAO.CartDAO;
import org.example.DAO.UserDAO;
import org.example.model.Cart;
import org.example.model.User;
import org.example.utils.RSAUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/user/register.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;
        String fullName = null;
        String email = null;
        String phone = null;
        String address = null;
        String profileImagePath = null;

        UserDAO userDAO = new UserDAO();
        // Đường dẫn thư mục tải ảnh
        String uploadPath = request.getServletContext().getRealPath("/") + "images/avatars";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        try {
            // Kiểm tra nếu yêu cầu chứa dữ liệu tải lên tệp
            if (ServletFileUpload.isMultipartContent(request)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Phân tích yêu cầu
                List<FileItem> formItems = upload.parseRequest(request);
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        // Xử lý dữ liệu văn bản
                        switch (item.getFieldName()) {
                            case "username":
                                username = item.getString("UTF-8");
                                break;
                            case "password":
                                password = item.getString("UTF-8");
                                break;
                            case "information":
                                fullName = item.getString("UTF-8");
                                break;
                            case "email":
                                email = item.getString("UTF-8");
                                break;
                            case "phone":
                                phone = item.getString("UTF-8");
                                break;
                            case "address":
                                address = item.getString("UTF-8");
                                break;
                        }
                    } else {
                        // Xử lý tệp tải lên
                        // Lấy phần mở rộng của tệp gốc
                        String originalFileName = new File(item.getName()).getName();
                        String fileExtension = "";
                        int lastDotIndex = originalFileName.lastIndexOf('.');
                        if (lastDotIndex > 0 && lastDotIndex < originalFileName.length() - 1) {
                            fileExtension = originalFileName.substring(lastDotIndex); // Lấy phần mở rộng, bao gồm dấu "."
                        }

                        // Đổi tên tệp thành img<userId>
                        String newFileName = "img" + userDAO.getCurrentAutoIncrementId() + fileExtension;
                        String filePath = uploadPath + File.separator + newFileName;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                        profileImagePath = "/images/avatars/" + newFileName;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Đã xảy ra lỗi trong quá trình xử lý!");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }


        List<User> userList = null;
        userList = userDAO.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                request.setAttribute("message", "Tên đăng nhập đã tồn tại.");
                request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            }
        }
        PublicKey publicKey = RSAUtil.getPublicKey(); // Lấy khóa công khai RSA
        String encryptedPassword = RSAUtil.encrypt(password, publicKey); // Mã hóa mật khẩu

        // Tạo người dùng, giỏ hàng
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encryptedPassword); // Lưu mật khẩu đã mã hóa
        newUser.setName(fullName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setProfileImage(profileImagePath);


        // Thêm người dùng
        userDAO.addUser(newUser);

        CartDAO cartDAO = new CartDAO();
        Cart newCart = new Cart();
        User currentUser = userDAO.getUserByUsername(username);
        newCart.setCustomerId(currentUser.getUserId());
        cartDAO.addCart(newCart);

        request.setAttribute("message", "Đăng ký thành công! Hãy chuyển đến trang đăng nhập");
        request.setAttribute("messageType", "success");
        request.getRequestDispatcher("views/user/register.jsp").forward(request, response);

    }
}

