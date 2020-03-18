package com.example.pavilion.Provider;

import com.alibaba.fastjson.JSON;
import com.example.pavilion.dto.AccessTokenDTO;
import com.example.pavilion.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//把当前类初始化到Spring的容器的上下文   加了以后不需要GithubProvider githubProvider = new GithubProvider() 这样写
//会把对象自动的实例化 放到一个池子里面
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {//做post请求   需要引入Jar包或引入maven
        //传递过来的accessTokenDTO是一个类，需要转换成json，这时候就又需要引入一个jar包
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));//用Application-json的方式去获取access_token
        //需要把json转换成string.json(下载的依赖就是转换用的)
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            //return response.body().string();
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //通过access_token去获取user信息
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)//老方法
                .build();
//                .url("https://api.github.com/user")//新方法
//                .header("Authorization", "token"+accessToken)
//                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//parseObject把string自动转化成JAVA的类对象
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
