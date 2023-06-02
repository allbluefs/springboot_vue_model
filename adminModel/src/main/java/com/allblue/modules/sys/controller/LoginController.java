package com.allblue.modules.sys.controller;

import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.UserService;
import com.allblue.utils.R;
import com.allblue.utils.TokenUtils;
import com.allblue.utils.VerifyUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode", objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }


    @RequestMapping("/login")
    @ResponseBody
    public R login(@RequestBody UserEntity user) {
        UserEntity u = null;
        u = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", user.getUsername()));
        if (null == u) {
            return R.error("用户信息不存在");
        } else {
            if (!user.getPassword().equals(u.getPassword())) {
                return R.error("账号或密码错误");
            }
        }
        if (u.getStatus().equals(0)) {
            return R.error("账户已被封禁");
        }
        String token = TokenUtils.genToken(u);
        u.setToken(token);
        List<Object> permissions= new ArrayList<>();
        permissions.add("/home");
        permissions.add("/user");
        permissions.add("/menu");
        u.setPermissions(permissions);
        return R.ok("登录成功").put("user", u);
    }

    @RequestMapping("/register")
    @ResponseBody
    public R register(@RequestBody UserEntity user) {
        UserEntity userDB = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", user.getUsername()));
        if (userDB != null) {
            return R.error("用户名已存在");
        }
        user.setStatus(1);
        user.setCreateTime(new Date());
        userService.save(user);
        return R.ok("注册成功");

    }

    /**
     * 退出登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("sys/logout")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return "login";
    }

    /**
     * 获取登录用户的值
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLoginUserInfo")
    @ResponseBody
    public Object getLoginUserInfo(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }

}
