package com.ylb.controller;

import com.ylb.common.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    // 图片存储路径：项目根目录/uploads/
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        // 1. 准备文件夹
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 2. 生成唯一文件名 (防止重名覆盖)
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + suffix;

        // 3. 保存文件
        file.transferTo(new File(uploadDir + newFileName));

        // 4. 返回可访问的 URL (需要配置静态资源映射)
        // 假设后端端口8080，映射路径为 /images/**
        String url = "http://localhost:8080/images/" + newFileName;
        return Result.success(url);
    }
}