package com.allblue.modules.sys.service.impl;

import com.allblue.modules.sys.dao.MenuDao;
import com.allblue.modules.sys.entity.MenuEntity;
import com.allblue.modules.sys.service.MenuService;
import com.allblue.modules.sys.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    //    @Autowired
//    private UserMenuService userMenuService;
    @Autowired
    private UserService userService;

    @Override
    public List<MenuEntity> getUserMenuTree(Long userId) {
        List<MenuEntity> list;
        //系统管理员，拥有最高权限
        if (userId == 1) {
            list = this.selectList(new EntityWrapper<MenuEntity>().orderBy("order_num"));
        } else {
            //用户菜单列表
            List<Long> menuIdList = userService.queryAllMenuId(userId);
            list = this.selectList(new EntityWrapper<MenuEntity>().in("menu_id", menuIdList).orderBy("order_num"));
        }
        return getChildList(0L, list);
    }

    private List<MenuEntity> getChildList(Long parentId, List<MenuEntity> list) {
        List<MenuEntity> children = new ArrayList<>();
        for (MenuEntity menu : list) {
            if (menu.getParentId().equals(parentId)) {
                menu.setChildren(getChildList(menu.getMenuId(), list));
                children.add(menu);
            }
        }
        return children;
    }

    @Override
    public List<MenuEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<MenuEntity> getTreeMenu() {
        List<MenuEntity> parentMenu = this.selectList(new EntityWrapper<MenuEntity>().eq("parent_id", 0));
        return getChild(parentMenu);
    }

    private List<MenuEntity> getChild(List<MenuEntity> menuList){
        for (MenuEntity menuEntity : menuList) {
            List<MenuEntity> childMenuList = this.selectList(new EntityWrapper<MenuEntity>()
                    .eq("parent_id", menuEntity.getMenuId()));
            if (CollectionUtils.isNotEmpty(childMenuList)) {
                getChild(childMenuList);
                menuEntity.setChildren(childMenuList);
            }
        }
        return menuList;
    }

}
