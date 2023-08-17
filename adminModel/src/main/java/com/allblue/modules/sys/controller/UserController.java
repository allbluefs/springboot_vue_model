package com.allblue.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.allblue.modules.sys.entity.MenuEntity;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.UserService;

import com.allblue.utils.ContextHolderUtils;
import com.allblue.utils.PageUtils;
import com.allblue.utils.R;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    public R info(@PathVariable("userId") Long userId) {
        UserEntity user = userService.selectById(userId);
        List<MenuEntity> perms = userService.queryAllPerms(user.getUserId());
        user.setPermissions(perms);
        return R.ok().put("user", user);
    }

    /**
     * 切换状态
     */
    @RequestMapping("/changeIsUse/{userId}")
    public R changeIsUse(@PathVariable("userId") Long userId) {
        UserEntity user = userService.selectById(userId);
        user.setStatus(user.getStatus() == 1 ? 0 : 1);
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user) {
        if (user.getPassword().length() < 6) {
            return R.error("密码至少6位");
        }
        userService.save(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user) {
        userService.update(user);
        UserEntity userDB = userService.selectById(user.getUserId());
        return R.ok().put("user",userDB);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        userService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteById/{userId}")
    public R deleteById(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);
        return R.ok();
    }

    /**
     * 用户名检测
     */
    @RequestMapping("/detect")
    public R detect(UserEntity user) {
        if (user.getUserId() == null) {
            int count = userService.selectCount(new EntityWrapper<UserEntity>().eq("username", user.getUsername()));
            if (count > 0) {
                return R.ok().put("valid", false);
            }
        } else {
            int count = userService.selectCount(new EntityWrapper<UserEntity>().eq("username", user.getUsername()).ne("user_id", user.getUserId()));
            if (count > 0) {
                return R.ok().put("valid", false);
            }
        }

        return R.ok().put("valid", true);
    }

    /**
     * 修改密码
     */
    @RequestMapping("/updatePassword")
    public R password(@RequestParam Map<String, Object> params) {
        String password = (String) params.get("password");
        String newPassword = (String) params.get("newPassword");
        UserEntity user = ContextHolderUtils.getUser();
        if (!user.getPassword().equals(password)) {
            R.error("密码错误");
        }
        user.setPassword(newPassword);
        userService.updateById(user);
        System.out.println(params);
        return R.ok();
    }
}
