package org.example.servlet.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/downloadTool")
public class DownloadToolServlet extends HttpServlet {
    private static final String FILE_PATH = "tools/tools.exe";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đường dẫn thực tế của file trên server
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            response.getWriter().write("File không tồn tại!");
            return;
        }

        // Cấu hình HTTP Header để tải file
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"SignatureTools.exe\"");

        // Ghi dữ liệu từ InputStream vào OutputStream
        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            inputStream.close();
        }
    }
}