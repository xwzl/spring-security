package com.security.demo.controller.file;

import com.security.demo.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author xuweizhi
 * @since 2019/05/12 15:08
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {

        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(System.currentTimeMillis() + ".txt");
        // 写入本地文件
        file.transferTo(localFile);

        FileInfo fileInfo = new FileInfo(localFile.getAbsolutePath());
        return fileInfo;
    }

    @GetMapping("/{id}")
    public void downLoad(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (InputStream in = new FileInputStream(new File("security-demo/1557645965219.txt"));
             OutputStream out = response.getOutputStream();) {
            response.setContentType("application/x-download");
            // 下载文件名
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");
            // 把文件信息写入 outStream
            IOUtils.copy(in, out);
            out.flush();
        }
    }

}
