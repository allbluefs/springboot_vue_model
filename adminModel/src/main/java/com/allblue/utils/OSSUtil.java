package com.allblue.utils;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * OSS上传工具类-张晗-2017/10/10
 */
public class OSSUtil {
    private final static String END_POINT = "oss-cn-beijing.aliyuncs.com";
    private final static String ACCESS_KEY_ID = "LTAI5t6PV37ffnUhhXPPfBRh";
    private final static String ACCESS_KEY_SECRET = "PhWsQXtK3D0QJXpqHhrbknyY7Lb0jq";
    private final static String BUCKET_NAME = "bucket-allblue";

    private OSSUtil() {
    }

    private volatile static OSS ossClient;

    static {
        if (ossClient == null) {
            synchronized (OSSUtil.class) {
                if (ossClient == null) {
                    ossClient = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
                }
            }
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件
     */
    public static String upload(MultipartFile file) {
        String firstKey = UUID.fastUUID() + "." + FileNameUtil.getSuffix(file.getOriginalFilename());
        try {
            ossClient.putObject(BUCKET_NAME, firstKey, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return firstKey;
    }

}