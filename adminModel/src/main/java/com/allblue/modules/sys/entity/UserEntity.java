package com.allblue.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName(value = "sys_user",resultMap = "tUserMap")
@Data
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long userId;
	/**
	 * 账户
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户姓名
	 */
	private String chineseName;
	private Integer userType;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	/**
	 * 1.正常 0.封禁
	 */
	private Integer status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@TableField(exist = false)
	private String token;
	@TableField(exist = false)
	private List<Object> permissions;
}
