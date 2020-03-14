package com.example.pavilion.controller;

import com.example.pavilion.mapper.UserMapper;
import com.example.pavilion.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller//作为spring的病句管理作用，并识别这个类是controller类（允许这个类接受前端的请求）
public class IndexController {

    //@GetMapping("/")，给mapping一个匹配的路径，一个“/”代表根，当什么都没有输入的时候就默认访问下面的index模板
    //    public String hello(@RequestParam(name = "name" ) String name , Model model){//ctrl+P获取RequestParam的参数
    //    model.addAttribute("name",name);//把浏览器传过来的值放到model里面，
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    //获取token需要注入Http  其中request是请求服务器，response返回浏览器
    public String index(HttpServletRequest request) {
        //p18 实现持久化登陆状态获取 （现在已经把token写到了token变量里面了，所以在访问首页的时候，我们需要把Cookie里面key为token的
        // 信息获取到，然后到数据库中查询，看数据库中是否存在，以此来验证它是否登陆成功）
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);//传一个token，就能获取一个user对象 user对象不为空说明登录成功
                if (user != null) {//不为空就写到session里面
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }
}
