package com.example.pavilion.mapper;

import com.example.pavilion.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//ctrl+alt+o 自动移除无用的引入
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId}" +
            ",#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

}
