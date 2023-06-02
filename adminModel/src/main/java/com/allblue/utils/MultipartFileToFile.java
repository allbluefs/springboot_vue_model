package com.allblue.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class MultipartFileToFile {
	public static void ToFile(MultipartFile multiFile, String dest) {
		// 获取文件名
		String fileName = multiFile.getOriginalFilename();
		// 获取文件后缀
		String prefix = fileName.substring(fileName.lastIndexOf("."));
		// 用当前时间作为文件名，防止生成的临时文件重复
		try {
			File file = new File(dest);
			multiFile.transferTo(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}