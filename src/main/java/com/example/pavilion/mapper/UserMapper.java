package com.example.pavilion.mapper;

import com.example.pavilion.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//ctrl+alt+o 自动移除无用的引入
@Mapper
public interface UserMapper {

    @Insert("insert into  user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId}" +
            ",#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    //如果定义形参的是类 形参里面的token会自动放到#{}里面，如果不是类定义的形参需要加一个注解@Param
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
