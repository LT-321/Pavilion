package com.example.pavilion.mapper;

import com.example.pavilion.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {//shift+F6 改变所有依赖（发现错误后改的比较方便）

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    void create(Question question);
}
