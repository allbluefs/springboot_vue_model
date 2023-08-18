package com.allblue.modules.sys.controller;

import com.allblue.utils.OSSUtil;
import com.allblue.utils.R;
import com.allblue.utils.UploadUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {
    /**
     * 保存
     */
    @RequestMapping("/uploadFile")
    public R uploadFile(MultipartFile file) {
        //上传图片
        String fileName = OSSUtil.upload(file);
        Map<String, String> data = new HashMap<>();
        data.put("src", "https://bucket-allblue.oss-cn-beijing.aliyuncs.com/" + fileName);
        return R.ok().put("data", data);
    }
}
