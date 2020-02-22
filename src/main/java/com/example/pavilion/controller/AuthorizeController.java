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
//通过application.properties配置
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//shift+回车 直接换行
        githubProvider.getAccessToken(accessTokenDTO);//ctrl+alt+v 快速创建变量
        accessTokenDTO.setClient_id("clientId");
        accessTokenDTO.setClient_secret("clientSecret");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("redirectUri");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        //由此往上是第二步：通过access_token 携带code去获取access_token
        return "index";
    }

}
