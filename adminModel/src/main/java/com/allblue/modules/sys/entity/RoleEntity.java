package com.allblue.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 */
@TableName(value = "sys_role",resultMap = "roleMap")
@Data
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    @TableField(exist = false)
    private List<Long> menuIdList;
}
