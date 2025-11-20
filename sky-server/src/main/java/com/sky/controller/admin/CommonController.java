package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/admin/common")
public class CommonController {

    private final AliOssUtil aliOssUtil;

    public CommonController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    @PostMapping("/upload")
//    如果名称不同, 用@requestparam注解
    public Result<String> upload(MultipartFile file) {
        log.info("file: {}", file);
        try {
            String originalFilename = file.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + substring;
            String upload = aliOssUtil.upload(file.getBytes(), filename);
            log.info("upload: {}", upload);
            return Result.success(upload);
        } catch(Exception e) {
            log.error("Exception: {}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
