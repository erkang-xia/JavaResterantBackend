package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/upload")
    @ApiOperation("upload 上传图片")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file.getOriginalFilename());
        // TODO : add aliOSS auth
        try {
//            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            String objectName = UUID.randomUUID().toString() + extName;
//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
//
//            return Result.success(filePath);

            return Result.success(file.getOriginalFilename());
        } catch (Exception e) {
            log.error("file upload error", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }



}
