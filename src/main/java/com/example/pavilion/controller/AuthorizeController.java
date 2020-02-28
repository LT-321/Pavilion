package com.example.pavilion.controller;

import com.example.pavilion.Provider.GithubProvider;
import com.example.pavilion.dto.AccessTokenDTO;
import com.example.pavilion.dto.GithubUser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//如何oAuth授权登录
//1. Request a user's GitHub identity
//2. Users are redirected back to your site by GitHub
//3. Use the access token to access the API


//完成授权之后正常返回社区的功能，即获取code
@Controller //把当前的类作为路由API的承载者
public class AuthorizeController {

    @Autowired//自动把容器内写好的实例加载到当前上下文
    private GithubProvider githubProvider;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request)
            //session是在request中拿到的

    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id("5ca82caf96ec46a5d3b1");
        accessTokenDTO.setClient_secret("2c67518d79312de365ccefa69d9c7b4f7e6f4b9d");
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if (user != null){
            request.getSession().setAttribute("user",user);//银行账户创建成功
            return "redirect:/";//把地址重定向到8080
            //登陆成功 写cookie和session
        }else {
            //登陆失败 重新登陆
            return "redirect:/";
        }
    }

}
