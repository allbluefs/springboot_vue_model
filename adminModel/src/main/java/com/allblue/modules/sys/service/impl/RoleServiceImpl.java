package com.allblue.modules.sys.service.impl;

import com.allblue.modules.sys.dao.RoleDao;
import com.allblue.modules.sys.entity.RoleEntity;
import com.allblue.modules.sys.entity.RoleMenuEntity;
import com.allblue.modules.sys.service.RoleMenuService;
import com.allblue.modules.sys.service.RoleService;
import com.allblue.modules.sys.shiro.ShiroUtils;
import com.allblue.utils.PageUtils;
import com.allblue.utils.Query;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

//    @Autowired
//    private DeptService deptService;
    @Autowired
    private RoleMenuService roleMenuService;
//    @Autowired
//    private RoleAppMenuService roleAppMenuService;
//    @Autowired
//    private RoleDeptService roleDeptService;
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Page<RoleEntity> page = this.selectPage(
                new Query<RoleEntity>(params).getPage(),
                new EntityWrapper<RoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
        );
        return new PageUtils(page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(RoleEntity role) {
        role.setCreateTime(new Date());
        this.insert(role);

        saveRoleMenuRelation(role);
//        saveRoleDeptRelation(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleEntity role) {
        this.updateById(role);
        saveRoleMenuRelation(role);
//        saveRoleDeptRelation(role);
    }

    @Override
    public List<RoleEntity> getRoleByUserId(Long userId) {
        return roleDao.getRoleByUserId(userId);
    }

    @Override
    public List<RoleEntity> selectAll(Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        Long role_id;
        //系统管理员，拥有最高权限
        if (userId == 1) {
            role_id = 0l;
        } else {
            RoleEntity role = selectByUserId(userId);
            role_id = role.getRoleId();
        }

        return roleDao.selectList(new EntityWrapper<RoleEntity>());
    }

    @Override
    @Transactional
    public void deleteBatchIds(Long[] roleIds) {
        this.deleteBatchIds(Arrays.asList(roleIds));
        roleMenuService.deleteBatch(Arrays.asList(roleIds));
        roleMenuService.deleteBatch(Arrays.asList(roleIds));
    }

    @Override
    public Long getRoleIdByName(String roleName) {
        return baseMapper.getRoleIdByName(roleName);
    }

    @Override
    public RoleEntity selectByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    /**
     * 先解除之前角色和机构关系，再绑定关系
     *
     * @param role
     */
//    private void saveRoleDeptRelation(RoleEntity role) {
//        List<Long> roleIds = new ArrayList<>(1);
//        roleIds.add(role.getRoleId());
//
//        List<Long> deptIdList = role.getDeptIdList();
//        if (deptIdList.size() == 0) {
//            return;
//        }
//
//    }

    /**
     * 选解除之前角色和菜单关系，再绑定关系
     *
     * @param role
     */
    private void saveRoleMenuRelation(RoleEntity role) {
        List<Long> roleIds = new ArrayList<>(1);
        roleIds.add(role.getRoleId());
        roleMenuService.deleteBatch(roleIds);

        List<Long> menuIdList = role.getMenuIdList();
        if (menuIdList.size() == 0) {
            return;
        }

        List<RoleMenuEntity> roleMenuEntities = new ArrayList<>(menuIdList.size());
        for (int i = 0; i < menuIdList.size(); i++) {
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setRoleId(role.getRoleId());
            roleMenuEntity.setMenuId(menuIdList.get(i));
            roleMenuEntities.add(roleMenuEntity);
        }
        roleMenuService.insertBatch(roleMenuEntities);
    }

//    /**
//     * 选解除之前角色和菜单关系，再绑定关系
//     *
//     * @param role
//     */
//    private void saveRoleAppMenuRelation(RoleEntity role) {
//        roleAppMenuService.delete(new EntityWrapper<RoleAppMenuEntity>().eq("role_id", role.getRoleId()));
//        List<Long> menuIdList = role.getAppMenuIdList();
//        if (menuIdList.size() == 0) {
//            return;
//        }
//
//        List<RoleAppMenuEntity> roleMenuEntities = new ArrayList<>(menuIdList.size());
//        for (int i = 0; i < menuIdList.size(); i++) {
//            RoleAppMenuEntity roleMenuEntity = new RoleAppMenuEntity();
//            roleMenuEntity.setRoleId(role.getRoleId());
//            roleMenuEntity.setMenuId(menuIdList.get(i));
//            roleMenuEntities.add(roleMenuEntity);
//        }
//        roleAppMenuService.insertBatch(roleMenuEntities);
//    }
}
