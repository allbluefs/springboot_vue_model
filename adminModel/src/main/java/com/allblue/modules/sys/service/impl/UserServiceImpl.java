package com.allblue.modules.sys.service.impl;

import com.allblue.modules.sys.dao.UserDao;
import com.allblue.modules.sys.entity.MenuEntity;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.entity.UserRoleEntity;
import com.allblue.modules.sys.service.MenuService;
import com.allblue.modules.sys.service.UserRoleService;
import com.allblue.modules.sys.service.UserService;
import com.allblue.modules.sys.shiro.ShiroUtils;
import com.allblue.utils.PageUtils;
import com.allblue.utils.Query;
import com.allblue.utils.RRException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MenuService menuService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        EntityWrapper<UserEntity> ew = new EntityWrapper<>();
        ew.like(StringUtils.isNotBlank(username),"username",username);
        ew.ne("user_id",1);
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(), ew);
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserEntity user) {
        //sha256加密
        this.insert(user);

        saveUserRoleRelation(user);
    }
    @Override
    public void update(UserEntity user) {
        this.updateById(user);
        saveUserRoleRelation(user);
    }
    private void saveUserRoleRelation(UserEntity user) {
        UserRoleEntity userRoleEntity = userRoleService.selectByUserId(user.getUserId());
        if (userRoleEntity == null) {
            userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(user.getUserId());
            userRoleEntity.setRoleId(user.getRoleId());
            userRoleService.insert(userRoleEntity);
        } else {
            userRoleEntity.setUserId(user.getUserId());
            userRoleEntity.setRoleId(user.getRoleId());
            userRoleService.updateAllColumnById(userRoleEntity);
        }

    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {

        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public List<MenuEntity> queryAllPerms(Long userId) {
        List<MenuEntity> permsList;
        //系统管理员，拥有最高权限
        if (userId == 1) {
            List<MenuEntity> menuList = menuService.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (MenuEntity menu : menuList) {
                permsList.add(menu);
            }
        } else {
            permsList = this.baseMapper.queryAllPerms(userId);
        }

        return permsList;
    }
}
