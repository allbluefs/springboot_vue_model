package com.allblue.modules.sys.controller;

import com.allblue.modules.sys.entity.LoginForm;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.LoginService;
import com.allblue.modules.sys.service.UserService;
import com.allblue.modules.sys.shiro.CustomToken;
import com.allblue.modules.sys.shiro.ShiroUtils;
import com.allblue.utils.R;
import com.allblue.utils.RRException;
import com.allblue.utils.VerifyUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.subject.Subject;
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
    @Autowired
    private LoginService loginService;

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
    public R login(@RequestBody LoginForm loginForm) {
        UserEntity user = loginService.login(loginForm);
        return R.ok("登录成功").put("user", user);
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
