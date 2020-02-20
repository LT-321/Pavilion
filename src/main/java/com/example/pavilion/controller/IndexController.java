package com.example.pavilion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller//作为spring的病句管理作用，并识别这个类是controller类（允许这个类接受前端的请求）
public class IndexController {
    //@GetMapping("/")，给mapping一个匹配的路径，一个“/”代表根，当什么都没有输入的时候就默认访问下面的index模板
    @GetMapping("/")
    //    public String hello(@RequestParam(name = "name" ) String name , Model model){//ctrl+P获取RequestParam的参数
    //    model.addAttribute("name",name);//把浏览器传过来的值放到model里面，
    public String index() {
        return "index";
    }
}
