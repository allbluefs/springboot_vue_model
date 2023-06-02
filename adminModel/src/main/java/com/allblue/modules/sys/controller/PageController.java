package com.allblue.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public Object home() {
        return "login";
    }
    @RequestMapping("/user")
    public Object user() {
        return "modules/sys/user";
    }
    @RequestMapping("/index")
    public Object index2() {
        return "index";
    }
    @RequestMapping("index.html")
    public Object index() {
        return "index";
    }
    @RequestMapping("register.html")
    public Object register() {
        return "register";
    }
    @RequestMapping("login.html")
    public Object login() {
        return "login";
    }
    @RequestMapping("main.html")
    public String main() {
        return "main";
    }
    @RequestMapping("modules/{url}.html")
    public String module( @PathVariable("url") String url) {
        return "modules/"+ url;
    }
    @RequestMapping("modules/{module}/{url}.html")
    public String module2(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "modules/" + module + "/" + url;
    }
}
