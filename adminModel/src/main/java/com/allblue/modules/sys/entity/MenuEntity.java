package com.allblue.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 */
@TableName("sys_menu")
@Data
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@TableId
	private Long menuId;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;
	/**
	 * 菜单名称
	 */
	private String name;
	private String content;
	/**
	 * 菜单URL
	 */
	private String path;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;
	/**
	 * 类型   0：目录   1：菜单   2：按钮
	 */
	private Integer type;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer orderNum;
	@TableField(exist=false)
	private List<MenuEntity> children;
	/**
	 * 父菜单名称
	 */
	@TableField(exist=false)
	private String parentName;

    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;

}
