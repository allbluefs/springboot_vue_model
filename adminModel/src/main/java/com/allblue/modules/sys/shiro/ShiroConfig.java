package com.allblue.modules.sys.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    // 权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    //将自己的验证方式加入容器
    @Bean
    public UserRealm myShiroRealm() {
        return new UserRealm();
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 创建一个shiroFilter实例
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 配置Manager
        shiroFilter.setSecurityManager(securityManager);
        // 自定义过滤规则,可以自定义多个, 通过map传入
        Map<String, Filter> filters = new HashMap<>();
        // 第一个参数过滤器, 第二个参数是具体的过滤器实例
        filters.put("myFilter", new MyFilter());
        // 将自定义的过滤器加入shiroFilter中
        shiroFilter.setFilters(filters);
        // 上面两个方法不能很全面,所以可以通过LinkedHashMap向过滤器传入过滤配置, 因为存入的规则是有序的
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 第一个参数是路径, 第二个参数字典值,标识过滤规则
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        // 需要注意匹配按照上下顺序来的,  如果/**写在最前面那么,后面的都被覆盖了
        // 登出
        filterMap.put("/sys/logout", "logout");
        // 对所有用户认证
        filterMap.put("/login", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/**", "myFilter");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        // 设置登录路径,设置后登录路径下不走认证,也可以用下面kv的形式写入
        shiroFilter.setLoginUrl("/");
        // 首页
        shiroFilter.setSuccessUrl("/index.html");
        // 设置权限不足是跳转页面
        shiroFilter.setUnauthorizedUrl("/error.html");
        return shiroFilter;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
