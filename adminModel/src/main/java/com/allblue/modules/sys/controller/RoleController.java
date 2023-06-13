package com.allblue.modules.sys.controller;

import com.allblue.modules.sys.entity.RoleEntity;
import com.allblue.modules.sys.service.RoleMenuService;
import com.allblue.modules.sys.service.RoleService;
import com.allblue.utils.PageUtils;
import com.allblue.utils.R;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 角色
 */
@RestController
@RequestMapping("sys/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params) {
        PageUtils page = roleService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/select")
    public R select(@RequestParam Map<String, Object> params) {
//        List<RoleEntity> roleEntities = roleService.selectList(new EntityWrapper<RoleEntity>());
        List<RoleEntity> roleEntities = roleService.selectAll(params);
        return R.ok().put("list", roleEntities);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    public R info(@PathVariable("roleId") Long roleId) {
        RoleEntity role = roleService.selectById(roleId);

        List<Long> menuIdList = roleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);



        return R.ok().put("role", role);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RoleEntity role) {

        roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RoleEntity role) {
        roleService.update(role);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] roleIds) {
        roleService.deleteBatchIds(roleIds);

        return R.ok();
    }

    /**
     * 检测
     */
    @RequestMapping("/detect")
    public R delete(RoleEntity roleEntity) {
        if (roleEntity.getRoleId() == null) {
            int count = roleService.selectCount(new EntityWrapper<RoleEntity>().eq("role_name", roleEntity.getRoleName()));
            if (count > 0) {
                return R.ok().put("valid", false);
            }
        }else {
            int count = roleService.selectCount(new EntityWrapper<RoleEntity>().eq("role_name", roleEntity.getRoleName()).ne("role_id", roleEntity.getRoleId()));
            if (count > 0) {
                return R.ok().put("valid", false);
            }
        }

        return R.ok().put("valid", true);
    }
}
