//package com.allblue.config;
//
//import cn.hutool.log.Log;
//import cn.hutool.log.LogFactory;
//import com.allblue.utils.R;
//import com.allblue.utils.RRException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice(basePackages = "com.allblue.modules.sys")
//public class GlobalExceptionHandler {
//
//    private static final Log log = LogFactory.get();
//
//    //统一异常处理@ExceptionHandler,主要用于Exception
//    @ExceptionHandler(RRException.class)
//    @ResponseBody//返回json串
//    public R customer(HttpServletRequest request, RRException e) {
//        return R.error(e.getCode(), e.getMsg());
//    }
//
//    //统一异常处理@ExceptionHandler,主要用于Exception
//    @ExceptionHandler(Exception.class)
//    @ResponseBody//返回json串
//    public R error(HttpServletRequest request, Exception e) {
//        log.error("异常信息：", e);
//        return R.error(500, "系统异常");
//    }
//
//
//}
