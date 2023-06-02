package com.allblue.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Component
public class UploadUtils {

    public static String fileUrl;

    @Value("${file.location}")
    private String tempFileUrl;

    @PostConstruct
    public void setFileUrl() {
        UploadUtils.fileUrl = this.tempFileUrl;
    }

    /**
     * 上传图片
     *
     * @return 图片id
     */
    public static String Upload(MultipartFile file) {
        if (file != null) {
            String fileName = System.currentTimeMillis() + "";
            String rootPath = fileUrl;
            MultipartFileToFile.ToFile(file, rootPath + fileName);
            return fileName;
        }
        return null;
    }
}
